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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import tanat.androidtesttask.R;
import tanat.androidtesttask.errorreporter.GetLogs;
import tanat.androidtesttask.utils.LoadLocalData;
import tanat.androidtesttask.utils.SendEMail;

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
    LoadLocalData loadSD = new LoadLocalData(MainActivity.this);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        // Check the availability of the SD card
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            if (i == R.id.save_logs) {
                if (isCheckPermission(1)) {
                    saveLogs();
                }
            } else if (i == R.id.send_logs) {
                if (isCheckPermission(2)) {
                    if (!loadSD.existFile(LOG_FILE_NAME)) {
                        saveLogs();
                    }
                    new SendEMail(MainActivity.this).send(LOG_FILE_NAME);
                }
            }
        } else {
            Toast.makeText(this, "Insert a memory card", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean sendMail = false;

    private boolean isCheckPermission(int i) {
        if (i == 1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showMessageOKCancel("Requires write permission to save logs to file");
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
        } else if (i == 2) {
            //
            sendMail = true;

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showMessageOKCancel("Requires read permission to transfer file");
                    return false;
                }
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST);
                return false;
            }
            return true;
        }
        return false;
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
                            MainActivity.this, new String[]{
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST);
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveLogs();
                if (sendMail) {
                    new SendEMail(MainActivity.this).send(LOG_FILE_NAME);
                    sendMail = false;
                }
            }
        }
    }

    public void saveLogs (){
        loadSD.writeFileSD(LOG_FILE_NAME, GetLogs.get());
        Toast.makeText(this, "Logs save", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        loadSD.writeFileSD(LOG_FILE_NAME, GetLogs.get());
        super.onDestroy();
    }
}