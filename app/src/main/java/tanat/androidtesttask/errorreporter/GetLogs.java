package tanat.androidtesttask.errorreporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetLogs {

    public static String get(){
        StringBuilder builder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d all");

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line + "\n");
            }
        } catch (IOException e){
            Log.d("Error getting logs");
        }
        return builder.toString();
    }
}
