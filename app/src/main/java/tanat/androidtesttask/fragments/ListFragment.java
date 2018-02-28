package tanat.androidtesttask.fragments;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import tanat.androidtesttask.BuildConfig;
import tanat.androidtesttask.R;
import tanat.androidtesttask.activity.InfoRoutActivity;
import tanat.androidtesttask.activity.MainActivity;
import tanat.androidtesttask.database.DBHelper;
import tanat.androidtesttask.errorreporter.Log;
import tanat.androidtesttask.model.RealmModel;
import tanat.androidtesttask.service.BroadcastService;
import tanat.androidtesttask.utils.JSONParsing;
import tanat.androidtesttask.utils.LoadLocalData;

public class ListFragment extends android.app.ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.standart_layout)
    LinearLayout contentLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.errorTextView)
    TextView errorTextView;

    //button to update if an error
    @OnClick(R.id.refreshButton)
    void onRefreshClick() {
        onRefresh();
    }

    private View rootView;

    private final static int STATUS_START = 100;
    private final static int STATUS_FINISH = 200;
    private final static String PARAM_RESULT = "result";
    private final static String PARAM_STATUS = "status";
    public final static String BROADCAST_ACTION = "tanat.androidtesttask.activity";
    private BroadcastReceiver broadcastReceiver;

    private LoadLocalData loadLocalData;
    DBHelper dbHelper;
    Realm realm;

    //connect my fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item, null);
        ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setOnRefreshListener(this);

        intent = new Intent(getActivity(), BroadcastService.class);
        setsConnection();

        loadLocalData = new LoadLocalData(getActivity());

        dbHelper = new DBHelper(getActivity());

        Realm.init(getActivity());

        setRetainInstance(true);
        return rootView;
    }

    Intent intent;
    private ArrayList data = null;

    private void setsConnection() {
        // create BroadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            // actions when receiving messages
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                // catch messages about the start of task
                if (status == STATUS_START) {
                    if (BuildConfig.USE_LOG) {
                        Log.d("server start task");
                    }
                }
                // catch messages about the finish of task
                if (status == STATUS_FINISH) {
                    if (BuildConfig.USE_LOG) {
                        Log.d("server finish task");
                    }

                    data = intent.getStringArrayListExtra(PARAM_RESULT);
                    procesShowData();

                    saveCache();
                }
            }
        };
        // create filter BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);

        //register BroadcastReceiver
        getActivity().registerReceiver(broadcastReceiver, intFilt);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        procesShowData();
    }

    @Override
    public void onPause() {
        // save the data so that you do not load it again when return to the fragment
        if (data != null && !data.get(0).toString().equals("false")) {
            //save data in file
            new LoadLocalData(getActivity()).writeFile(FILE_NAME, new JSONParsing().dispatch());
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        //deregister BroadcastReceiver
        getActivity().unregisterReceiver(broadcastReceiver);
        //stop service
        getActivity().stopService(intent);
        super.onDestroy();
    }

    //hang the listener on the click of the fragment
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(ListFragment.this.getContext(), InfoRoutActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    // swipe down for updates
    @Override
    public void onRefresh() {
        // start show progress dialog
        swipeRefreshLayout.setRefreshing(true);

        // updates data
        getActivity().startService(intent);
    }

    private String FILE_NAME = "JsonTestTask";

    // loading data
    private void loadData() {
        // start show progress dialog
        swipeRefreshLayout.setRefreshing(true);

        // upload local data
        data = loadLocalData.returnArray(FILE_NAME);

        //if there is no data, load it from the network
        if (data == null || data.size() == 0 || data.get(0).toString().equals("false")) {
            data = null;
            getActivity().startService(intent);
        }
    }

    private void procesShowData() {
        if (data != null) {
            /* data for the list is
             * now we will check whether the network operation was performed correctly*/
            if (data.get(0).toString().equals("false")) {
                /* network operation failed
                 * change the visibility layout so that the working layout was not visible and was visible
                 * error layout and enter the error text in textView*/
                contentLayout.setVisibility(View.INVISIBLE);
                errorLayout.setVisibility(View.VISIBLE);
                errorTextView.setText(data.get(1).toString());
            } else {
                // if network operation true
                errorLayout.setVisibility(View.INVISIBLE);
                contentLayout.setVisibility(View.VISIBLE);
                // create list
                createdList();
            }
        } else {
            // if data is null
            errorLayout.setVisibility(View.INVISIBLE);
            contentLayout.setVisibility(View.VISIBLE);
            // create list (ListFragment automatically outputs the specified message)
            createdList();
        }
        if (data != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private ArrayAdapter<String> adapter;

    // create list
    public void createdList() {
        if (data != null) {
            adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, data);
        } else {
            adapter = null;
        }
        setListAdapter(adapter);
    }

    public void saveCache() {
        int checkTypeDatabase = new MainActivity().checkTypeDatabase;
        if (checkTypeDatabase == 1) {
            // use Realm database
            CacheRealm cacheRealm = new CacheRealm();
            cacheRealm.execute();

        } else if (checkTypeDatabase == 2) {
            // use SQLite database
            CacheSQLite cacheSQLite = new CacheSQLite();
            cacheSQLite.execute();
        }
    }

    private int id = 0;

    private int name_from_city = 1;
    private int highlight_from_city = 2;
    private int id_from_city = 3;

    private int name_to_city = 4;
    private int highlight_to_city = 5;
    private int id_to_city = 6;

    private int info = 7;

    private int from_date = 8;
    private int from_time = 9;
    private int from_info = 10;

    private int to_date = 11;
    private int to_time = 12;
    private int to_info = 13;

    private int price = 14;
    private int bus_id = 15;
    private int reservation_count = 16;

    // Caching the query in the Realm database
    class CacheRealm extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<String[]> arrayList = new JSONParsing().examineJSONObj();

            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();

            try {
                // open realm transaction
                realm.beginTransaction();

                // clear realm from
                RealmResults<RealmModel> results = realm.where(RealmModel.class).findAll();
                results.deleteAllFromRealm();

                for (int i = 0; i < arrayList.size(); i++) {
                    // create realm model
                    RealmModel realmObject = realm.createObject(RealmModel.class);

                    realmObject.setId(Integer.valueOf(arrayList.get(i)[id]));

                    realmObject.setName_from_city(arrayList.get(i)[name_from_city]);
                    realmObject.setHighlight_from_city(arrayList.get(i)[highlight_from_city]);
                    realmObject.setId_from_city(Integer.valueOf(arrayList.get(i)[id_from_city]));

                    realmObject.setName_to_city(arrayList.get(i)[name_to_city]);
                    realmObject.setHighlight_to_city(arrayList.get(i)[highlight_to_city]);
                    realmObject.setId_to_city(Integer.valueOf(arrayList.get(i)[id_to_city]));

                    realmObject.setInfo(arrayList.get(i)[info]);

                    realmObject.setFrom_date(arrayList.get(i)[from_date]);
                    realmObject.setFrom_time(arrayList.get(i)[from_time]);
                    realmObject.setFrom_info(arrayList.get(i)[from_info]);

                    realmObject.setTo_date(arrayList.get(i)[to_date]);
                    realmObject.setTo_time(arrayList.get(i)[to_time]);
                    realmObject.setTo_info(arrayList.get(i)[to_info]);

                    realmObject.setPrice(Integer.valueOf(arrayList.get(i)[price]));
                    realmObject.setBus_id(Integer.valueOf(arrayList.get(i)[bus_id]));
                    realmObject.setReservation_count(Integer.valueOf(arrayList.get(i)[reservation_count]));
                }

                // close realm transaction
                realm.commitTransaction();

            } finally {
                // close realm
                realm.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (BuildConfig.USE_LOG) {
                Log.d("The cache is written to the Realm database");
            }
            super.onPostExecute(result);
        }
    }

    // Caching the query in the SQLite database
    class CacheSQLite extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<String[]> arrayList = new JSONParsing().examineJSONObj();

            // create db exemplar SQLiteDatabase
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // create values exemplar ContentValues
            ContentValues values = new ContentValues();

            // clear table
            db.delete(dbHelper.TABLE_NAME, null, null);

            for (int i = 0; i < arrayList.size(); i++) {
                values.put(dbHelper.ID, arrayList.get(i)[id]);
                values.put(dbHelper.NAME_FROM_CITY, arrayList.get(i)[name_from_city]);
                values.put(dbHelper.HIGHLIGHT_FROM_CITY, arrayList.get(i)[highlight_from_city]);
                values.put(dbHelper.ID_FROM_CITY, arrayList.get(i)[id_from_city]);
                values.put(dbHelper.NAME_TO_CITY, arrayList.get(i)[name_to_city]);
                values.put(dbHelper.HIGHLIGHT_TO_CITY, arrayList.get(i)[highlight_to_city]);
                values.put(dbHelper.ID_TO_CITY, arrayList.get(i)[id_to_city]);
                values.put(dbHelper.INFO, arrayList.get(i)[info]);
                values.put(dbHelper.FROM_DATE, arrayList.get(i)[from_date]);
                values.put(dbHelper.FROM_TIME, arrayList.get(i)[from_time]);
                values.put(DBHelper.FROM_INFO, arrayList.get(i)[from_info]);
                values.put(DBHelper.TO_DATE, arrayList.get(i)[to_date]);
                values.put(dbHelper.TO_TIME, arrayList.get(i)[to_time]);
                values.put(dbHelper.TO_INFO, arrayList.get(i)[to_info]);
                values.put(dbHelper.PRICE, arrayList.get(i)[price]);
                values.put(dbHelper.BUS_ID, arrayList.get(i)[bus_id]);
                values.put(dbHelper.RESERVATION_COUNT, arrayList.get(i)[reservation_count]);

                // insert values in table
                db.insert(dbHelper.TABLE_NAME, null, values);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (BuildConfig.USE_LOG) {
                Log.d("The cache is written to the SQLite database");
            }
            super.onPostExecute(result);
        }
    }
}
