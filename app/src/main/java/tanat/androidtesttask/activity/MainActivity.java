package tanat.androidtesttask.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import tanat.androidtesttask.R;
import tanat.androidtesttask.errorreporter.GetLogs;
import tanat.androidtesttask.utils.LoadLocalData;

public class MainActivity extends Activity {

    private FirebaseAnalytics mFirebaseAnalytics;
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        context = MainActivity.this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public String LOG_FILE_NAME = "logs";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String action = "";

        int i = item.getItemId();
        if (i == R.id.save_logs) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (isCheckPermission()) {
                    LoadLocalData loadSD = new LoadLocalData(MainActivity.this);
                    loadSD.writeFileSD(LOG_FILE_NAME, GetLogs.get());
                    action = "logs save";
                } else {
                    action = String.valueOf(R.string.permission_not_received);
                }
            }

        } else if (i == R.id.send_logs) {

        }

        Toast.makeText(this, action, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private boolean isCheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageOKCancel("Do you want to save logs");
                return false;
            }

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST);

            return false;
            // MY_PERMISSIONS_REQUEST is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        return true;
    }

    private void showMessageOKCancel(String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", listener)
                .create()
                .show();
    }

    final private int PERMISSIONS_REQUEST = 7;

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        final int BUTTON_NEGATIVE = -2;
        final int BUTTON_POSITIVE = -1;

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_NEGATIVE:
                    // int which = -2
                    dialog.dismiss();
                    break;

                case BUTTON_POSITIVE:
                    // int which = -1
                    ActivityCompat.requestPermissions(
                            MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST);
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onDestroy() {
        Log.d("MyLog","ТЕСТ ТЕСТ TEST TEST ТЕСТ ТЕСТ TEST TEST ТЕСТ ТЕСТ TEST TEST");
        Log.d("Logs","Logs: " + GetLogs.get());

        super.onDestroy();
    }
}