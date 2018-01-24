package tanat.androidtesttask.utils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LoadLocalData {

    private static Context context;

    public LoadLocalData(Context context) {
        this.context = context;
    }

    // name file local database
    // переменная в которой записано название файла что представляет собой локальную строку json
    private static String FILENAME = "jsonmytest";

    //method save string in file
    //метод для сохранение строки json в файл (создание локальной базы)
    public void writeFile(String answer) {
        try {
            //open the stream for writing (отрываем поток для записи)
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE)));
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
    public static String readFile() {
        String str = "";
        try {
            //open stream from read (открываем поток для чтения)
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(FILENAME)));
            // read file (читаем содержимое)
            str = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList returnArray (){
        return new JSONParsing().examineJSONDemoString(readFile());
    }
}