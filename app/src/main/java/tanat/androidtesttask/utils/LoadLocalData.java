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

import tanat.androidtesttask.errorreporter.Log;

public class LoadLocalData {

    private static Context context;

    public LoadLocalData(Context context) {
        this.context = context;
    }

    // name file local database
    // переменная в которой записано название файла что представляет собой локальную строку json


    //method save string in file
    //метод для сохранение строки json в файл (создание локальной базы)
    public void writeFile(String fileName, String answer) {
        try {
            //open the stream for writing (отрываем поток для записи)
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE)));
            // writing data (пишем данные)
            bw.write(answer);
            // close stream (закрываем поток)
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method read string from a file
    // метод для чтения строки json из файла
    public static String readFile(String fileName) {
        String str = "";
        try {
            //open stream from read (открываем поток для чтения)
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            // read file (читаем содержимое)
            str = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList returnArray (String fileName){
        return new JSONParsing().examineJSONDemoString(readFile(fileName));
    }

    private String DIR_SD = "/Android/data/androidtesttask/logs";

    public void writeFileSD(String fileName, String logsStr) {
        // проверяем доступность SD
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + DIR_SD);
            // создаем каталог
            sdPath.mkdirs();
            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath, fileName);
            try {
                // открываем поток для записи
                BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                // пишем данные
                bw.write(logsStr);
                // закрываем поток
                bw.close();
                Log.d("File save on SD: " + sdFile.getAbsolutePath());
            } catch (IOException e) {
                Log.d(e.getMessage());
            }
        } else {
            writeFile(fileName, logsStr);
            Log.d("SD card not available, file save on mobile storage");
        }
    }

    public String readFileSD(String fileName) {
        String logsStr = "";
        // проверяем доступность SD
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath, fileName);
            try {
                // открываем поток для чтения
                BufferedReader br = new BufferedReader(new FileReader(sdFile));
                // читаем содержимое
                while ((logsStr = br.readLine()) != null) {
                    logsStr = logsStr + "\n";
                }
                return logsStr;
            } catch (FileNotFoundException e) {
                logsStr = logsStr + e.getMessage();
            } catch (IOException e) {
                logsStr = logsStr + e.getMessage();
            }
        } else {
            logsStr = readFile(fileName);
        }
        return logsStr;
    }
}