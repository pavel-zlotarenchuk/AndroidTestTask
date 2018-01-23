package tanat.androidtesttask.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import tanat.androidtesttask.service.ConectService;

public class LoadAllData {

    private final Context context;

    public LoadAllData(Context context) {
        this.context = context;
    }

    private static String jsonStr = "";

    public void loadStringData (Context context){

        jsonStr = loadLocalData(context);

        if (jsonStr == null || jsonStr.equals("")){
            jsonStr = loadInetData(context);
        }
    }

    public ArrayList loadDemoData (Context context, int numberOfDownloads){
        ArrayList demoData = null;

        // проверяем загружали ли данные в список раньше
        if (numberOfDownloads == 0){
            loadStringData(context);
        } else {
            // обновляем данные
            loadInetData(context);
        }

        ArrayList demoFile = new JSONParsing().examineJSONDemoString(jsonStr);
        return demoData;
    }

    public String loadLocalData (Context context) {
        LoadLocalData loadLocalData;
        loadLocalData = new LoadLocalData(context);
        return loadLocalData.readFile();
    }

    public void pullLocalData (Context context, String localJsonStr) {
        LoadLocalData loadLocalData;
        loadLocalData = new LoadLocalData(context);
        loadLocalData.writeFile(localJsonStr);
    }

    boolean bound = false;
    ServiceConnection serviceConnection;
    Intent intent;
    ConectService conectService;

    public String loadInetData (Context context){
        intent = new Intent(context, ConectService.class);
        serviceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d("MyLog", "MainActivity onServiceConnected");
                conectService = ((ConectService.LocalBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d("MyLog", "MainActivity onServiceDisconnected");
                bound = false;
            }
        };
        conectService.bindService(intent, serviceConnection, 0);

        String inetJsonStr = null;
        inetJsonStr = conectService.getInetJsonStr();

        return inetJsonStr;
    }
}
