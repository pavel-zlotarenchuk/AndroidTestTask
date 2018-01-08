package tanat.androidtesttask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyService extends Service {

    static String strJson = "";
    InputStream inputStream;
    public String FILENAME = "jsonmytest";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected String someTask() {
        return strJson;
    }

    protected void someTaskOne() {
        new GetTask().execute();
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

                //                   inputStream = urlConnection.getInputStream();
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
            //здесь сохраняем строку json в файл
            try {
                // отрываем поток для записи
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        openFileOutput(FILENAME, MODE_PRIVATE)));
                // пишем данные
                bw.write(answer);
                // закрываем поток
                bw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    }

