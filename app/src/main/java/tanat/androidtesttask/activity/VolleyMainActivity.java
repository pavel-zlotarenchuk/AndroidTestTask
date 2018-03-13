package tanat.androidtesttask.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tanat.androidtesttask.R;
import tanat.androidtesttask.api.AppController;

public class VolleyMainActivity extends Activity {
    private ListView listView;
    String urlJsonObj = "http://projects.gmoby.org/web/index.php/api/trips?from_date=2016-01-01&to_date=2018-03-01";
    ArrayAdapter adapter;

    private String jsonResponse;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_main_activity);
        listView = (ListView) findViewById(R.id.listView);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        makeJsonObjectRequest();
    }


    public void onLoadFinished(ArrayList<String> data) {
        adapter = new ArrayAdapter<String>(VolleyMainActivity.this,
                android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }

    private void makeJsonObjectRequest() {
        final ArrayList<String> data = new ArrayList<>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                showpDialog();
                try {
                    JSONArray arrayData = response.getJSONArray("data");


                    for (int i = 0; i < arrayData.length(); i++) {
                        JSONObject jsonArrayRoute = new JSONObject(arrayData.getString(i));
                        String id = jsonArrayRoute.getString("id");

                        JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                        String nameFromCity = jsonArrayFromCity.getString("name");

                        JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                        String nameToCity = jsonArrayToCity.getString("name");

                        String fromDate = jsonArrayRoute.getString("from_date");
                        String toDate = jsonArrayRoute.getString("from_time");

                        data.add(id + " | " + nameFromCity + " | " + nameToCity + " | " + fromDate + " | " + toDate);
                    }

                    onLoadFinished(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
