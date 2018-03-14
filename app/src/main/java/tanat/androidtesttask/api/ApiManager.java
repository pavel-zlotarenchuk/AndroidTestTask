package tanat.androidtesttask.api;


import android.accounts.NetworkErrorException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.security.auth.login.LoginException;

import io.reactivex.Observable;

public class ApiManager {

    public static Observable loadObservable() {
        return Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws JSONException, LoginException, IOException {
                URL url = new URL("http://projects.gmoby.org/web/index.php/api/trips?from_date=2016-01-01&to_date=2018-03-01");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                ArrayList demoData = new ArrayList();
                Boolean success;
                String id;
                String nameFromCity;
                String nameToCity;
                String fromDate;
                String toDate;
                String message;

                JSONObject post = new JSONObject(buffer.toString());
                success = post.getBoolean("success");
                if (success) {
                    JSONArray arrayData = post.getJSONArray("data");

                    for (int i = 0; i < arrayData.length(); i++) {
                        JSONObject jsonArrayRoute = new JSONObject(arrayData.getString(i));
                        id = jsonArrayRoute.getString("id");

                        JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                        nameFromCity = jsonArrayFromCity.getString("name");

                        JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                        nameToCity = jsonArrayToCity.getString("name");

                        fromDate = jsonArrayRoute.getString("from_date");
                        toDate = jsonArrayRoute.getString("from_time");

                        demoData.add(id + " | " + nameFromCity + " | " + nameToCity + " | " + fromDate + " | " + toDate);
                    }
                } else {
                    message = post.getString("message_error");
                    throw new LoginException(message);
                }
                return demoData;
            }
        });
    }
}
