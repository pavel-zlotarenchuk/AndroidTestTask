package tanat.androidtesttask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    public static String jsonStr = "";
    public String FILENAME = "jsonmytest";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonStr = readFile();
        //делаем проверку на наличие локальной базы
        if (jsonStr == null){
           loadData();
        }
    }

    public void onStart() {
        //сохраняем локальную базу
        write(jsonStr);
        super.onStart();
    }

    public void loadData(){
        //скачиваем базу
        jsonStr = new MyService().someTask();
     }

/*    @Override // Здесь вы создаете все диалоги
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_LOAD_KEY: {
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Загрузка, подождите пожалуйста...");
                dialog.setCancelable(true);
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        loadData(); // Вызов вашей функции загрузки
                        // Удаление диалога после загрузки
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });

                    }

                }).start();
                return dialog;
            }
        }
        return super.onCreateDialog(id);
    }*/

    @Override
    public void onClick(View v) {
    }

    public String readFile() {
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            // читаем содержимое
            str = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    protected void write(String answer) {
            //здесь сохраняем строку json в файл
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME, MODE_PRIVATE)));
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

    public ArrayList demo (){



        loadData();
        ArrayList demoFile = new JSONFile().examineJSONDemoString(jsonStr);
        return demoFile;
    }

/*    private class GetTask extends AsyncTask<Void, Void, String> {

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
    }*/
}