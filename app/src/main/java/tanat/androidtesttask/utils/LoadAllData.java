package tanat.androidtesttask.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.ArrayList;

import tanat.androidtesttask.activity.MainActivity;
import tanat.androidtesttask.service.ConectService;

public class LoadAllData {

    private final Context context;

    public LoadAllData(Context context) {
        this.context = context;
    }

    public ArrayList loadDemoData (int numberOfDownloads){
        String jsonStr = "";

        // проверяем загружали ли данные в список раньше
        if (numberOfDownloads == 0) {
            // если нет - загружаем из локального хранилища
            jsonStr = loadLocalData();
            //делаем проверку на наличие локальной базы
            if (jsonStr == null || jsonStr.equals("")) {
                jsonStr = loadInetData();
            }
        } else {
            //если загружали данные раньше - обновляем их
            jsonStr = loadInetData();
        }

        ArrayList demoData = new JSONParsing().examineJSONDemoString(jsonStr);
        return demoData;
    }

    public String loadLocalData () {
        LoadLocalData loadLocalData;
        loadLocalData = new LoadLocalData(context);
        return loadLocalData.readFile();
    }

    public void pullLocalData (String localJsonStr) {
        LoadLocalData loadLocalData;
        loadLocalData = new LoadLocalData(context);
        loadLocalData.writeFile(localJsonStr);
    }

    private static boolean bound = false;
    private static ConectService conectService;

    public String loadInetData (){
        String result = null;
//        result = MainActivity.loadData();

        setsConnection();
        if (bound) {
            result = conectService.getInetJsonStr();
        }

        return result;
    }


    private Intent intent;
    private ServiceConnection sConnection;

    private void setsConnection (){
        sConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                ConectService.LocalBinder binder = (ConectService.LocalBinder) service;
                conectService = binder.getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName className) {
                bound = false;
            }
        };

        intent = new Intent(context, ConectService.class);
        context.bindService(intent, sConnection, Context.BIND_AUTO_CREATE);
        bound = true;
        context.startService(intent);
    }
}
