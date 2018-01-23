package tanat.androidtesttask.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import tanat.androidtesttask.activity.MainActivity;
import tanat.androidtesttask.service.ConectService;

public class LoadAllData {

    private final Context context;

    public LoadAllData(Context context) {
        this.context = context;
    }

    private static String jsonStr = "";

    public String loadStringData (Context context){
        String jsonStr = "";
        jsonStr = loadLocalData(context);

        if (jsonStr == null || jsonStr.equals("")){
    //        jsonStr = loadInetData(context);
        }

        return jsonStr;
    }

    public ArrayList loadDemoData (Context context, int numberOfDownloads){
        String jsonStr = "";

        if (numberOfDownloads == 0) {
            jsonStr = loadLocalData(context);
            //делаем проверку на наличие локальной базы
            if (jsonStr == null || jsonStr.equals("")) {
                jsonStr = MainActivity.loadData();
            }
        } else {
            jsonStr = MainActivity.loadData();
        }

        ArrayList demoData = new JSONParsing().examineJSONDemoString(jsonStr);
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

/*    boolean bound = false;
    ServiceConnection serviceConnection;
    Intent intent;
    ConectService conectService;

    public void loadInetData (Context context){
        intent = new Intent(context, ConectService.class);
        serviceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
                conectService = ((ConectService.LocalBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
                bound = false;
            }
        };

        bindService(intent, serviceConnection, 0);
//        conectService.startService(intent);

        String inetJsonStr = null;
        inetJsonStr = conectService.getInetJsonStr();

        return inetJsonStr;
    }*/
}
