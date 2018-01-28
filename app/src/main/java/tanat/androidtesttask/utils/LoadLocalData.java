package tanat.androidtesttask.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import tanat.androidtesttask.BuildConfig;
import tanat.androidtesttask.errorreporter.Log;

public class LoadLocalData {

    private static Context context;

    public LoadLocalData(Context context) {
        this.context = context;
    }

    // name file local database
    // переменная в которой записано название файла что представляет собой локальную строку json


    // save string in file
    //метод для сохранение строки json в файл (создание локальной базы)
    public void writeFile(String fileName, String answer) {
        try {
            //open the stream for writing (отрываем поток для записи)
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE)));
            // writing data (пишем данные)
            bw.write(answer);
            // close stream (закрываем поток)
            bw.close();
            if (BuildConfig.USE_LOG) {Log.d("JSON string save on memory device");}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read string from a file
    // метод для чтения строки json из файла
    public static String readFile(String fileName) {
        String str = "";
        try {
            //open stream from read (открываем поток для чтения)
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            // read file (читаем содержимое)
            str = br.readLine();
            if (BuildConfig.USE_LOG) {Log.d("JSON string read with memory device");}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    // returning a list
    public ArrayList returnArray (String fileName){
        return new JSONParsing().examineJSONDemoString(readFile(fileName));
    }

    // path to the file directory
    private String DIR_SD = "/Android/data/androidtesttask/logs/";

    // writing to SD
    public void writeFileSD(String fileName, String logsStr) {
        //we get the path to SD
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            //add your directory to the path
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + DIR_SD);
            // create directory
            // создаем каталог
            sdPath.mkdirs();
            // create file in directory
            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath, fileName);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                bw.write(logsStr);
                bw.close();
                if (BuildConfig.USE_LOG) {Log.d("File save on SD: " + sdFile.getAbsolutePath());}
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    // checking for a file
    public Boolean existFile (String fileName){
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + DIR_SD);
        File sdFile = new File(sdPath, fileName);

        if (sdFile.exists()){
            if (BuildConfig.USE_LOG) {Log.d("File exists");}
            return true;
        }
        if (BuildConfig.USE_LOG) {Log.d("File not exists");}
        return false;
    }
}