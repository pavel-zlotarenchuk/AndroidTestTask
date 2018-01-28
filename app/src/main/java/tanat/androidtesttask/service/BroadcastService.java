package tanat.androidtesttask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import tanat.androidtesttask.errorreporter.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tanat.androidtesttask.BuildConfig;
import tanat.androidtesttask.fragments.ListFragment;
import tanat.androidtesttask.utils.JSONParsing;

public class BroadcastService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    ExecutorService executorService;

    public void onCreate() {
        super.onCreate();
        executorService = Executors.newFixedThreadPool(1);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        Run stream = new Run();
        executorService.execute(stream);

        return super.onStartCommand(intent, flags, startId);
    }

    class Run implements Runnable {

        public void run() {
            int STATUS_START = 100;
            int STATUS_FINISH = 200;
            String PARAM_RESULT = "result";
            String PARAM_STATUS = "status";
            Intent intent = new Intent(ListFragment.BROADCAST_ACTION);

            // inform about the start of the task
            intent.putExtra(PARAM_STATUS, STATUS_START);
            sendBroadcast(intent);

            // start task
            String strJson = "";
            InputStream inputStream;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Get the data from an external resource and re-do it on the line
            try {
                URL url = new URL("http://projects.gmoby.org/web/index.php/api/trips?from_date=2016-01-01&to_date=2018-03-01");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                inputStream = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                strJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                strJson = e.getMessage();
                if (BuildConfig.USE_LOG) {Log.d(e.getMessage());}
                FirebaseCrash.report(e);
            }

            ArrayList data = new JSONParsing().examineJSONDemoString(strJson);

            // inform about the finish of the task
            intent.putExtra(PARAM_STATUS, STATUS_FINISH);
            intent.putExtra(PARAM_RESULT, data);
            sendBroadcast(intent);
        }
    }
}
