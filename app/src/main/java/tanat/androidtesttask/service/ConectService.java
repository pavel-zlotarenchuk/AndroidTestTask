package tanat.androidtesttask.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ConectService extends Service {

    public void onCreate() {
        super.onCreate();
        Log.d("MyLog", "onCreateService");
    }

    private final IBinder mBinder = new LocalBinder();
    private static String inetJsonStr;

    public int onStartCommand(Intent intent, int flags, int startId) {
        GetTask getTask = new GetTask();
        getTask.execute();
        try {
            inetJsonStr = getTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void someTask() {

    }

    public String getInetJsonStr() {
        return inetJsonStr;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public ConectService getService() {
            return ConectService.this;
        }
    }

    private class GetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
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
            return strJson;
        }
    }
}
