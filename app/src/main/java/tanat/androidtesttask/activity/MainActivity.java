package tanat.androidtesttask.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import tanat.androidtesttask.service.ConectService;
import tanat.androidtesttask.R;
import tanat.androidtesttask.utils.LoadAllData;

public class MainActivity extends Activity {

    final static String LOG_TAG = "MyLog";

    private static boolean bound = false;
    private Intent intent;
    private static ConectService conectService;
    private ServiceConnection sConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //биндинг сервера
/*        sConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                ConectService.LocalBinder binder = (ConectService.LocalBinder) service;
                conectService = binder.getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName className) {
                Log.e(LOG_TAG, "onServiceDisconnected");
                bound = false;
            }
        };

        intent = new Intent(this, ConectService.class);
        bindService(intent, sConnection, Context.BIND_AUTO_CREATE);
        bound = true;
        startService(intent);*/
    }

    private static String jsonStr = "";

    // method for loading the database
    // метод для загрузки базы
    public static String loadData(){

        //биндинг сервера
/*        if (bound) {
            jsonStr = conectService.getInetJsonStr();
        }*/

        Log.d(LOG_TAG, "loadData");
        return jsonStr;
     }

    public void onStop () {
        super.onStop();
        //save the local database
/*        LoadAllData loadAllData = new LoadAllData(this);
        loadAllData.pullLocalData(jsonStr);

        stopService(intent);

        if (!bound) return;
        unbindService(sConnection);
        bound = false;*/
    }
}