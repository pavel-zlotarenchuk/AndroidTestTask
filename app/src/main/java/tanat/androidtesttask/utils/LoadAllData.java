package tanat.androidtesttask.utils;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

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

        if (numberOfDownloads == 0) {
            jsonStr = loadLocalData();
            //делаем проверку на наличие локальной базы
            if (jsonStr == null || jsonStr.equals("")) {
                jsonStr = loadInetData();
            }
        } else {
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

    private boolean bound = false;
    private static ServiceConnection sConn;
    private Intent intent;
    private static ConectService conectService;

    public String loadInetData (){
        return MainActivity.loadData();
    }
}
