package tanat.androidtesttask.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import tanat.androidtesttask.BuildConfig;
import tanat.androidtesttask.activity.InfoRoutActivity;
import tanat.androidtesttask.R;
import tanat.androidtesttask.service.BroadcastService;
import tanat.androidtesttask.utils.JSONParsing;
import tanat.androidtesttask.utils.LoadLocalData;
import tanat.androidtesttask.errorreporter.Log;

public class ListFragment extends android.app.ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.standart_layout) LinearLayout contentLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.errorTextView) TextView errorTextView;

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

    //connect my fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item, null);
        ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setOnRefreshListener(this);

        intent = new Intent(getActivity(), BroadcastService.class);
        setsConnection();

        setRetainInstance(true);
        return rootView;
    }

    Intent intent;
    private ArrayList data = null;

    private void setsConnection (){
        // create BroadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            // actions when receiving messages
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                // catch messages about the start of task
                if (status == STATUS_START) {
                    if (BuildConfig.USE_LOG) {Log.d("server start task");}
                    FirebaseCrash.log("server start task");
                }
                // catch messages about the finish of task
                if (status == STATUS_FINISH) {
                    if (BuildConfig.USE_LOG) {Log.d("server finish task");}
                    FirebaseCrash.log("server finish task");

                    data = intent.getStringArrayListExtra(PARAM_RESULT);
                    procesShowData();
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
    public void onPause(){
        // save the data so that you do not load it again when return to the fragment
        if(data != null && !data.get(0).toString().equals("false")) {
            //save data in file
            loadLocalData.writeFile(FILE_NAME, new JSONParsing().dispatch());
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
        // save data in file
        loadLocalData.writeFile(FILE_NAME, new JSONParsing().dispatch());

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

    private LoadLocalData loadLocalData;
    private String FILE_NAME = "JsonTestTask";
    // loading data
    private void loadData (){
        // start show progress dialog
        swipeRefreshLayout.setRefreshing(true);

        // upload local data
        loadLocalData = new LoadLocalData(getActivity());
        data = loadLocalData.returnArray(FILE_NAME);

        //if there is no data, load it from the network
        if (data == null || data.size() == 0 || data.get(0).toString().equals("false")) {
            data = null;
    //        onRefresh();
            getActivity().startService(intent);
        }
    }

    private void procesShowData (){
        if(data != null){
            /* data for the list is
             * now we will check whether the network operation was performed correctly*/
            if(data.get(0).toString().equals("false")){
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
    public void createdList(){
        if (data != null){
            adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, data);
        } else {
            adapter = null;
        }
        setListAdapter(adapter);
    }
}
