package tanat.androidtesttask.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import tanat.androidtesttask.fragments.AlterDialog;
import tanat.androidtesttask.service.ConectService;
import tanat.androidtesttask.service.TestService;
import tanat.androidtesttask.utils.JSONParsing;
import tanat.androidtesttask.service.MyService;
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

    private boolean bound = false;
    private static ServiceConnection sConn;
    private Intent intent;
    private static ConectService conectService;

    FragmentTransaction fragmentTransaction;

    /** erstsrgsrgf. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, ConectService.class);
        sConn = new ServiceConnection() {
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

        bindService(intent, sConn, 0);
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

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == STATUS_FINISH) {
                jsonStr = data.getStringExtra(PARAM_RESULT);

                ListFragment listFragment = new ListFragment();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.listFragment, listFragment);
                fragmentTransaction.commit();
            }
        }*/



    public void onPause() {
        //сохраняем локальную базу
        LoadAllData loadAllData = new LoadAllData(this);
        loadAllData.pullLocalData(this, jsonStr);
        super.onPause();
    }

    // мктод для загрузки базы
    public static String loadData(){
 //      jsonStr = new MyService().someTask();
        Log.d(LOG_TAG, "loadData");
        jsonStr = conectService.getInetJsonStr();
        return jsonStr;
     }

/*    // переменная в которую будем записывать сколько раз загружали данные в ListFragment
    private static int numberOfDownloads = 0;

    public ArrayList demo (){
        // проверяем загружали ли данные в список раньше
        if (numberOfDownloads > 0){
            // обновляем данные
            loadData();
        }
        // иначе увеличиваем количество загрузок на 1 и передаем в ListFragment
        numberOfDownloads++;
        ArrayList demoFile = new JSONParsing().examineJSONDemoString(jsonStr);
        return demoFile;
    }*/

    public void onStop () {
        if (!bound) return;
        unbindService(sConn);
        bound = false;

        stopService(intent);
        super.onStop();
    }
}