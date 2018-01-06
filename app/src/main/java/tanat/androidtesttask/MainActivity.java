package tanat.androidtesttask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{

    public static String LOG_TAG = "my_log";
    public String resultJson = "";
    public String jsonStr = "";
    InputStream inputStream;
    public String FILENAME = "jsonmytest";

    Button btnXML;
    Button btnJSON;
    TextView tvData;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData = (TextView) findViewById(R.id.txtData);
        btnXML = (Button) findViewById(R.id.btnXML);
        btnXML.setOnClickListener(this);
        btnJSON = (Button) findViewById(R.id.btnJSON);
        btnJSON.setOnClickListener(this);

        //      new MyService().someTaskOne();

        //делаем проверку на наличие локальной базы
        if (readFile() == null){
            //если базы нету, скачиваем ее и сохраняем
            new GetTask().execute();
            new GetTask().onPostExecute(jsonStr);
        }
    }

    public void onStart() {




        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJSON:
//        resultJson = new JSONFile().examineJSONFile(is);
//        resultJson = new JSONFile().examineJSONFile(new MyService().someTask());
    //            new GetTask().execute();
                resultJson = new JSONFile().examineJSONString(readFile());
                tvData.setText(resultJson);
                break;
            case R.id.btnXML:
    //            new GetTask().execute();
    //            new MyService().someTaskOne();
    //            jsonStr = new MyService().someTask();
                tvData.setText(readFile());
    //            tvData.setText(new MyService().someTask());
                break;
        }

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

                jsonStr = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
                jsonStr += e.getMessage();
            }

            return jsonStr;
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

    public String readFile() {
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            // читаем содержимое
            str = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}