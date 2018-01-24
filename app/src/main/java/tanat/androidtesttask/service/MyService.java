package tanat.androidtesttask.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyService extends Service {

    private static String strJson = "";
    private InputStream inputStream;
    private String FILENAME = "jsonmytest";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String someTask() {
        new GetTask().execute();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return strJson;
    }

    private class GetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

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

