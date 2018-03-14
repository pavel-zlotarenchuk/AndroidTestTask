package tanat.androidtesttask.errorreporter;

import android.content.Context;

import org.json.JSONException;

import java.net.UnknownHostException;

import javax.security.auth.login.LoginException;

/**
 * Created by mac on 3/14/18.
 */

public class ErrorManager {
    Context context;

    public ErrorManager(Context context) {
        this.context = context;
    }

    public String errorType(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            return "Error internet connect. " + throwable.getMessage();
        } else if (throwable instanceof JSONException) {
            return "Error on server. " + throwable.getMessage();
        } else if (throwable instanceof LoginException) {
            return throwable.getMessage();
        }
        return "String";
    }
}
