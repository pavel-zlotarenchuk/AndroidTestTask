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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import tanat.androidtesttask.BuildConfig;
import tanat.androidtesttask.activity.InfoRoutActivity;
import tanat.androidtesttask.R;
import tanat.androidtesttask.activity.MainActivity;
import tanat.androidtesttask.errorreporter.GetLogs;
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
    //кнопка для обновления в случае ошибки
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
    //подключаем мой фрагмент
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item, null);
        ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setOnRefreshListener(this);

        intent = new Intent(getActivity(), BroadcastService.class);
        setsConnection();

        return rootView;
    }

    public String LOG_FILE_NAME = "logs";

    Intent intent;
    private ArrayList data = null;

    private void setsConnection (){
        // create BroadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            // actions when receiving messages
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                // catch messages about the start of task
                // ловим сообщения о старте задач
                if (status == STATUS_START) {
                    if (BuildConfig.USE_LOG) {Log.d("server start task");}
                    FirebaseCrash.log("server start task");
                }
                // catch messages about the finish of task
                // Ловим сообщения об окончании задач
                if (status == STATUS_FINISH) {
                    if (BuildConfig.USE_LOG) {Log.d("server finish task");}
                    FirebaseCrash.log("server finish task");

                    data = intent.getStringArrayListExtra(PARAM_RESULT);
                    procesShowData();
                }
            }
        };
        // create filter BroadcastReceiver
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);

        //register BroadcastReceiver
        // регистрируем (включаем) BroadcastReceiver
        getActivity().registerReceiver(broadcastReceiver, intFilt);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        procesShowData();
    }

    //hang the listener on the click of the fragment
    //вешаем слушатель на нажатие итема фрагмента
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // save data in file
        loadLocalData.writeFile(FILE_NAME, new JSONParsing().dispatch());

        //передаем позицию елемента в второе активити
        //создаем интент
        Intent intent = new Intent(ListFragment.this.getContext(), InfoRoutActivity.class);
        //записываем в него ключ и позицию
        intent.putExtra("position", position);
        //передаем
        startActivity(intent);
    }

    // swipe down for updates
    //свайп вниз для обновления
    @Override
    public void onRefresh() {
        // start show progress dialog
        // начинаем показывать прогресс диалог
        swipeRefreshLayout.setRefreshing(true);

        // updates data
        // обновляем данные
        getActivity().startService(intent);
    }

    private LoadLocalData loadLocalData;

    public String FILE_NAME = "JsonTestTask";
    // method for loading data
    // метод для загрузки данных
    private void loadData (){
        // start show progress dialog
        swipeRefreshLayout.setRefreshing(true);

        // upload local data
        // загрузить локальные данные
        loadLocalData = new LoadLocalData(getActivity());
        data = loadLocalData.returnArray(FILE_NAME);

        //if there is no data, load it from the network
        //если данных нет - загружаем из сети
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
            /* данные для списка есть
             * теперь проверим правильно ли была выполнена сетевая операция*/
            if(data.get(0).toString().equals("false")){
                /* network operation failed
                 * change the visibility layout so that the working layout was not visible and was visible
                 * error layout and enter the error text in textView*/
                /* сетевая операция не прошла
                 * меняем видимость layout так, что б не было видно рабочего layout и был виден
                 * layout ошибки и вводим текст ошибки в textView */
                contentLayout.setVisibility(View.INVISIBLE);
                errorLayout.setVisibility(View.VISIBLE);
                errorTextView.setText(data.get(1).toString());
            } else {
                // if network operation true
                //если сетевая операция прошла успешно
                errorLayout.setVisibility(View.INVISIBLE);
                contentLayout.setVisibility(View.VISIBLE);
                // create list
                // создаем список
                createdList();
            }
        } else {
            // if data is null
            //если данных списка нету
            errorLayout.setVisibility(View.INVISIBLE);
            contentLayout.setVisibility(View.VISIBLE);
            // create list (ListFragment automatically outputs the specified message)
            // создаем список (ListFragment автоматически выведет заданое сообщение)
            createdList();
        }
        if (data != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private ArrayAdapter<String> adapter;

    // method create list
    // метод создание списка
    public void createdList(){
        if (data != null){
            adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, data);
        } else {
            adapter = null;
        }
        setListAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(data != null && !data.get(0).toString().equals("false")) {
            //save data in file
            loadLocalData.writeFile(FILE_NAME, new JSONParsing().dispatch());
        }
        //deregister BroadcastReceiver
        // дерегистрируем (выключаем) BroadcastReceiver
        getActivity().unregisterReceiver(broadcastReceiver);
        //stop service
        getActivity().stopService(intent);
    }
}
