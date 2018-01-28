package tanat.androidtesttask.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import tanat.androidtesttask.BuildConfig;
import tanat.androidtesttask.errorreporter.Log;

public class SendEMail {

    private static Context context;

    public SendEMail (Context context) {
        this.context = context;
    }

    private String eMail = "pashok.pashkevich1995@gmail.com";
    private String subject = "AndroidTestTask Logs";
    private String emailtext = "Please, specify the problem";

    private String DIR_SD = "/Android/data/androidtesttask/logs/";

    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

    public void send (String fileName){
        emailIntent.setType("plain/text");

        // email developer
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { eMail });

        // subject
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

        // text email
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext);

        // file
        String uri = "file://"  + Environment.getExternalStorageDirectory() + DIR_SD + fileName;
        emailIntent.putExtra(android.content.Intent.EXTRA_STREAM,
                Uri.parse(uri));
        if (BuildConfig.USE_LOG) {Log.d("Uri: " + uri);}

        // go
        context.startActivity(Intent.createChooser(emailIntent,"Sending..."));
        if (BuildConfig.USE_LOG) {Log.d("Sending file...");}
    }
}
