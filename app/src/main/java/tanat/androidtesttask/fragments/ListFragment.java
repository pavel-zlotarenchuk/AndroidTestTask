package tanat.androidtesttask.fragments;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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

import tanat.androidtesttask.activity.InfoRoutActivity;
import tanat.androidtesttask.R;
import tanat.androidtesttask.service.BroadcastService;
import tanat.androidtesttask.utils.JSONParsing;
import tanat.androidtesttask.utils.LoadAllData;

public class ListFragment extends android.app.ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    // unbilder для роботы butterknife с фрагментом

    private View rootView;
    private DialogFragment dialogFragment;

    final String LOG_TAG = "MyLog";
    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;
    public final static String PARAM_TIME = "time";
    public final static String PARAM_TASK = "task";
    public final static String PARAM_RESULT = "result";
    public final static String PARAM_STATUS = "status";
    public final static String BROADCAST_ACTION = "tanat.androidtesttask.activity";
    BroadcastReceiver broadcastReceiver;

    @BindView(R.id.refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.standart_layout) LinearLayout contentLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.errorTextView) TextView errorTextView;

    //кнопка для обновления в случае ошибки
    @OnClick(R.id.refreshButton)
    void onRefreshClick() {
        onRefresh();
    }

    String result;
    private ArrayList data = null;

    //подключаем мой фрагмент
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item, null);
        ButterKnife.bind(this, rootView);

        dialogFragment = new AlterDialog();
        swipeRefreshLayout.setOnRefreshListener(this);

        intent = new Intent(getActivity(), BroadcastService.class);
        setsConnection();

        return rootView;
    }

    Intent intent;

    private void setsConnection (){
        // создаем BroadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                // Ловим сообщения о старте задач
                if (status == STATUS_START) {
                    Log.d(LOG_TAG, "start task");
                }
                // Ловим сообщения об окончании задач
                if (status == STATUS_FINISH) {
                    Log.d(LOG_TAG, "finish task");
                    data = intent.getStringArrayListExtra(PARAM_RESULT);
                    procesShowData();
                }
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);

        // регистрируем (включаем) BroadcastReceiver
        getActivity().registerReceiver(broadcastReceiver, intFilt);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // дерегистрируем (выключаем) BroadcastReceiver
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        procesShowData();
    }

    //вешаем слушатель на нажатие итема фрагмента
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //передаем позицию елемента в второе активити
        //создаем интент
        Intent intent = new Intent(ListFragment.this.getContext(), InfoRoutActivity.class);
        //записываем в него ключ и позицию
        intent.putExtra("position", position);
        //передаем
        startActivity(intent);
    }

    // переменная в которую будем записывать сколько раз загружали данные в ListFragment
    int numberOfDownloads = 0;

    //свайп вниз для обновления
    @Override
    public void onRefresh() {

        /* есть две реализации диалога прогреса,
         * одна представлена возможностями класса  SwipeRefreshLayout, являеться стандартной
         * вторая создана с помощью DialogFragment*/

        // начинаем показывать прогресс
        swipeRefreshLayout.setRefreshing(true);

         /*либо используем наш класс, для этого нужно его раскоментить и заменить true на false в
         * строке выше 'mSwipeRefreshLayout.setRefreshing(false);' */
 //       dialogFragment.show(getFragmentManager(), "");

        // прячем прогресс
        getActivity().startService(intent);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // вызываем обновление данных


  //            data = new JSONParsing().examineJSONDemoString(result);

/*                if(data != null){
                    // данные для списка есть
                    // теперь проверим правильно ли была выполнена сетевая операция
                    if(data.get(0).toString().equals("false")){
                        // сетевая операция не прошла
                        // меняем видимость layout так, что б не было видно рабочего layout и был виден
                        // layout ошибки и вводим текст ошибки в textView
                        contentLayout.setVisibility(View.INVISIBLE);
                        errorLayout.setVisibility(View.VISIBLE);
                        errorTextView.setText(data.get(1).toString());
                    } else {
                        //если сетевая операция прошла успешно
                        errorLayout.setVisibility(View.INVISIBLE);
                        contentLayout.setVisibility(View.VISIBLE);
                        // создаем список
                        createdList();
                    }
                } else {
                    //если данных списка нету
                    errorLayout.setVisibility(View.INVISIBLE);
                    contentLayout.setVisibility(View.VISIBLE);
                    // создаем список (ListFragment автоматически выведет заданое сообщение)
                    createdList();
                }

                swipeRefreshLayout.setRefreshing(false);
                //либо используем наш класс
                dialogFragment.dismiss();*/
            }
        }, 0);
    }

    private void loadData (){

/*        LoadAllData loadAllData = new LoadAllData(getActivity());
        result = loadAllData.loadDemoData(numberOfDownloads);
        numberOfDownloads++;
        */

        swipeRefreshLayout.setRefreshing(true);
        dialogFragment.show(getFragmentManager(), "");

        LoadAllData loadAllData = new LoadAllData(getActivity());
        if (numberOfDownloads == 0) {
            // если нет - загружаем из локального хранилища
            data = loadAllData.returnArray();
            //делаем проверку на наличие локальной базы
            if (data == null || data.size() == 0) {
                getActivity().startService(intent);
            }
        } else {
            //если загружали данные раньше - обновляем их
            getActivity().startService(intent);
        }
        numberOfDownloads++;
    }

    private void procesShowData (){
        if(data != null){
            // данные для списка есть
            // теперь проверим правильно ли была выполнена сетевая операция
            if(data.get(0).toString().equals("false")){
                // сетевая операция не прошла
                // меняем видимость layout так, что б не было видно рабочего layout и был виден
                // layout ошибки и вводим текст ошибки в textView
                contentLayout.setVisibility(View.INVISIBLE);
                errorLayout.setVisibility(View.VISIBLE);
                errorTextView.setText(data.get(1).toString());
            } else {
                //если сетевая операция прошла успешно
                errorLayout.setVisibility(View.INVISIBLE);
                contentLayout.setVisibility(View.VISIBLE);
                // создаем список
                createdList();
            }
        } else {
            //если данных списка нету
            errorLayout.setVisibility(View.INVISIBLE);
            contentLayout.setVisibility(View.VISIBLE);
            // создаем список (ListFragment автоматически выведет заданое сообщение)
            createdList();
        }

        swipeRefreshLayout.setRefreshing(false);
        //либо используем наш класс
        dialogFragment.dismiss();
    }

    private ArrayAdapter<String> adapter;

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
}
