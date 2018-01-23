package tanat.androidtesttask.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;

import tanat.androidtesttask.activity.MainActivity;

/**
 * Created by TaNaT on 23.01.2018.
 */

public class TestService extends Service {

    final String LOG_TAG = "myLogs";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    ExecutorService executorService;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");

        PendingIntent pendingIntent = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);
        LoadRun loadRun = new LoadRun();
        executorService.execute(loadRun);

        return super.onStartCommand(intent, flags, startId);
    }

    class LoadRun implements Runnable {
        PendingIntent pendingIntent;

        public void run() {

            Log.d(LOG_TAG, "LoadRun#");

            // начинаем выполнение задачи
            String strJson = "";
            InputStream inputStream;

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

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

                strJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                strJson = e.getMessage();
            }

            // сообщаем об окончании задачи
            Intent intent = new Intent().putExtra(MainActivity.PARAM_RESULT, strJson);
            try {
                pendingIntent.send(TestService.this, MainActivity.STATUS_FINISH, intent);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }
}
