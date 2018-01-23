package tanat.androidtesttask.utils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoadLocalData {

    private static Context context;

    public LoadLocalData(Context context) {
        this.context = context;
    }

    // переменная в которой записано название файла что представляет собой локальную строку json
    // или локальную базу
    private static String FILENAME = "jsonmytest";

    //метод для сохранение строки json в файл (создание локальной базы)
    public void writeFile(String answer) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE)));
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

    // метод для чтения строки json из файла
    public static String readFile() {
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(FILENAME)));
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