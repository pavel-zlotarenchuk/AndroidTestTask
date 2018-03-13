package tanat.androidtesttask.service;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by mac on 2/27/18.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
    }
}