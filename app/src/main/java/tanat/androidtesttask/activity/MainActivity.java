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
    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;
    public final static String PARAM_STATUS = "status";
    public final static String PARAM_RESULT = "result";
    public final static String PARAM_PINTENT = "pendingIntent";

    final static String LOG_TAG = "MyLog";

    // переменная в которую будем записывать json - строку (или ошибку загрузки, если такая есть)
    private static String jsonStr = "";

    private static boolean bound = false;
    private Intent intent;
    private static ConectService conectService;
    private ServiceConnection sConnection;

    /** erstsrgsrgf. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sConnection = new ServiceConnection() {
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
        startService(intent);

/*        AlterDialog alterDialog = new AlterDialog();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.listFragment, alterDialog);
        fragmentTransaction.commit();*/

/*        PendingIntent pendingIntent;
        Intent intent;

        pendingIntent = createPendingResult(0, null, 0);
        intent = new Intent(this, TestService.class).putExtra(PARAM_PINTENT, pendingIntent);
        startService(intent);*/
    }

    // мктод для загрузки базы
    public static String loadData(){

        if (bound) {
            jsonStr = conectService.getInetJsonStr();
        }

        Log.d(LOG_TAG, "loadData");
        return jsonStr;
     }

    public void onStop () {
        super.onStop();
        //сохраняем локальную базу
        LoadAllData loadAllData = new LoadAllData(this);
        loadAllData.pullLocalData(jsonStr);

        stopService(intent);

        if (!bound) return;
        unbindService(sConnection);
        bound = false;
    }
}