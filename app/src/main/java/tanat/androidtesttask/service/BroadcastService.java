package tanat.androidtesttask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tanat.androidtesttask.fragments.ListFragment;
import tanat.androidtesttask.utils.JSONParsing;

public class BroadcastService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    final String LOG_TAG = "MyLog";
    ExecutorService executorService;

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Service onCreate");
        executorService = Executors.newFixedThreadPool(1);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Service onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(LOG_TAG, "Service onStartCommand");

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
            // сообщаем о старте задачи
            intent.putExtra(PARAM_STATUS, STATUS_START);
            sendBroadcast(intent);

            // start task
            // начинаем выполнение задачи
            String strJson = "";
            InputStream inputStream;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Get the data from an external resource and re-do it on the line
            // получаем данные с внешнего ресурса и переделываем в строку
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
                Log.d(LOG_TAG, "strJson not null");
                strJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                strJson = e.getMessage();
                Log.d(LOG_TAG, strJson);
            }

            ArrayList data = new JSONParsing().examineJSONDemoString(strJson);

            // inform about the finish of the task
            // сообщаем об окончании задачи
            intent.putExtra(PARAM_STATUS, STATUS_FINISH);
            intent.putExtra(PARAM_RESULT, data);
            sendBroadcast(intent);
        }
    }
}
