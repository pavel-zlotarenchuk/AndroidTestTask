package tanat.androidtesttask.activity;

/**
 * Created by mac on 2/27/18.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tanat.androidtesttask.R;
import tanat.androidtesttask.adapter.ContactAdapter;
import tanat.androidtesttask.api.ApiService;
import tanat.androidtesttask.api.RetroClient;
import tanat.androidtesttask.model.Data;
import tanat.androidtesttask.model.PojoModel;
import tanat.androidtesttask.utils.InternetConnection;

public class NewMainActivity extends AppCompatActivity {

    private ListView listView;
    private View parentView;

    private ArrayList<Data> contactList;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_activity);

        contactList = new ArrayList<>();

        parentView = findViewById(R.id.parentLayout);

        listView = (ListView) findViewById(R.id.listView);

        Toast toast =
                Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull final View view) {

                    /**
                     * Checking Internet Connection
                     */
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        final ProgressDialog dialog;
                        /**
                         * Progress Dialog for User Interaction
                         */
                        dialog = new ProgressDialog(NewMainActivity.this);
                        dialog.setTitle("setTitle");
                        dialog.setMessage("setMessage");
                        dialog.show();

                        //Creating an object of our api interface
                        ApiService api = RetroClient.getApiService();

                        /**
                         * Calling JSON
                         */
                        Call<PojoModel> call = api.getMyJSON();

                        /**
                         * Enqueue Callback will be call when get response...
                         */
                        call.enqueue(new Callback<PojoModel>() {
                            @Override
                            public void onResponse(Call<PojoModel> call, Response<PojoModel> response) {
                                //Dismiss Dialog
                                dialog.dismiss();

                                if (response.isSuccessful()) {
                                    List<Data> dataList = response.body().getData();

                                    //contactList = response.body().getContacts();

                                    /**
                                     * Binding that List to Adapter
                                     */
                                    adapter = new ContactAdapter(NewMainActivity.this, dataList);
                                    listView.setAdapter(adapter);

                                } else {
                                    Snackbar.make(parentView, "Snackbar", Snackbar.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PojoModel> call, Throwable t) {

                            }
                        });

                    } else {
                        Snackbar.make(parentView, "Bad Snackbar", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
