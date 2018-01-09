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

    // переменная в которую будем записывать json - строку (или ошибку загрузки, если такая есть)
    public static String jsonStr = "";
    // переменная в которой записано название файла что представляет собой локальную строку json
    // или локальную базу
    public String FILENAME = "jsonmytest";
    // переменная в которую будем записывать сколько раз загружали данные в ListFragment
    public static int numberOfDownloads = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // записываем локальную строку json в переменную
        jsonStr = readFile();
        //делаем проверку на наличие локальной базы
        if (jsonStr == null || jsonStr.equals("")){
           loadData();
        }
    }

    public void onStart() {
        super.onStart();
    }

    public void onPause() {
        //сохраняем локальную базу
        write(jsonStr);
        super.onPause();
    }

    // мктод для загрузки базы
    public void loadData(){
        jsonStr = new MyService().someTask();
     }

    @Override
    public void onClick(View v) {
    }

    // метод для чтения строки json из файла
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

    //метод для сохранение строки json в файл (создание локальной базы)
    protected void write(String answer) {
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
        // проверяем загружали ли данные в список раньше
        if (numberOfDownloads > 0){
            // обновляем данные
            loadData();
        }
        // иначе увеличиваем количество загрузок на 1 и передаем в ListFragment
        numberOfDownloads++;
        ArrayList demoFile = new JSONFile().examineJSONDemoString(jsonStr);
        return demoFile;
    }
}