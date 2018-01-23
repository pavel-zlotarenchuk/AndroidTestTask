package tanat.androidtesttask.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import tanat.androidtesttask.service.ConectService;
import tanat.androidtesttask.utils.JSONParsing;
import tanat.androidtesttask.service.MyService;
import tanat.androidtesttask.R;
import tanat.androidtesttask.utils.LoadAllData;
import tanat.androidtesttask.utils.LoadLocalData;

public class MainActivity extends Activity {

    // переменная в которую будем записывать json - строку (или ошибку загрузки, если такая есть)
    private static String jsonStr = "";
    LoadLocalData loadLocalData;
    LoadAllData loadAllData;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAllData = new LoadAllData(this);
        jsonStr = loadAllData.loadLocalData(this);
//        loadLocalData = new LoadLocalData(this);
        // записываем локальную строку json в переменную
//        jsonStr = loadLocalData.readFile();
  //      jsonStr = readFile();
        //делаем проверку на наличие локальной базы
        if (jsonStr == null || jsonStr.equals("")){
           loadData();
        }


        startService(new Intent(this, ConectService.class));
    }

/*    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }*/

    public void onPause() {
        loadAllData.pullLocalData(this, jsonStr);
//        loadLocalData.writeFile(jsonStr);
        //сохраняем локальную базу
   //     write(jsonStr);
        super.onPause();
    }

    // мктод для загрузки базы
    public void loadData(){
        jsonStr = new MyService().someTask();
     }

    // переменная в которой записано название файла что представляет собой локальную строку json
    // или локальную базу
    private String FILENAME = "jsonmytest";

    // метод для чтения строки json из файла
    public String readFile() {
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            // открываем поток для чтения
     //       BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
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


    // переменная в которую будем записывать сколько раз загружали данные в ListFragment
    private static int numberOfDownloads = 0;

    public ArrayList demo (){
        // проверяем загружали ли данные в список раньше
        if (numberOfDownloads > 0){
            // обновляем данные
            loadData();
        }
        // иначе увеличиваем количество загрузок на 1 и передаем в ListFragment
        numberOfDownloads++;
        ArrayList demoFile = new JSONParsing().examineJSONDemoString(jsonStr);
        return demoFile;
    }


    public void onDestroy () {
        super.onDestroy();
        stopService(new Intent(this, ConectService.class));
    }
}