package tanat.androidtesttask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyService extends Service {

    String strJson;
    InputStream inputStream;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

/*    protected String someTask() {
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;
        String strJson = "";


        // получаем данные с внешнего ресурса
        try {
            URL url = new URL("https://api.androidhive.info/feed/feed.json");

            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
//            int str = urlConnection.getResponseCode();

            InputStream inputStream = urlConnection.getInputStream();
//            InputStream inputStream = url.openConnection().getInputStream();


            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            strJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            strJson += e.getMessage();
        }
        return strJson;
    }*/



    protected String someTask() {
        return strJson;
    }

    protected void someTaskOne() {
        new GetTask().execute();
    }

    private class GetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            HttpsURLConnection urlConnection = null;
            BufferedReader reader = null;

            // получаем данные с внешнего ресурса
            try {
                URL url = new URL("https://api.androidhive.info/feed/feed.json");

                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int str = urlConnection.getResponseCode();

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
                strJson += e.getMessage();
            }
            return strJson;
        }

        @Override
        protected void onPostExecute(String answer) {

                try {
                    FileOutputStream outputStream = openFileOutput("jsonmytest", MODE_PRIVATE);
                    outputStream.write(answer.getBytes());
                    outputStream.close();

                } catch (Exception e) {

                }
            }
        }
    }

