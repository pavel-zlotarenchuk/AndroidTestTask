package tanat.androidtesttask.fragments;

import android.app.DialogFragment;
import android.content.Intent;
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

import tanat.androidtesttask.activity.InfoRoutActivity;
import tanat.androidtesttask.R;
import tanat.androidtesttask.utils.LoadAllData;

public class ListFragment extends android.app.ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    // unbilder для роботы butterknife с фрагментом

    private View rootView;
    private DialogFragment dialogFragment;

    @BindView(R.id.refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.standart_layout) LinearLayout contentLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.errorTextView) TextView errorTextView;

    //кнопка для обновления в случае ошибки
    @OnClick(R.id.refreshButton)
    void onRefreshClick() {
        onRefresh();
    }

    //подключаем мой фрагмент
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item, null);
        ButterKnife.bind(this, rootView);

        dialogFragment = new AlterDialog();
        swipeRefreshLayout.setOnRefreshListener(this);

        return rootView;
    }

    private ArrayList data = null;

    @Override
    public void onStart() {
        super.onStart();
        onRefresh();
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
//
        dialogFragment.show(getFragmentManager(), "");

        // вызываем загрузку данных
//     data = new MainActivity().demo();



        // прячем прогресс
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                LoadAllData loadAllData = new LoadAllData(getActivity());
                data = loadAllData.loadDemoData(numberOfDownloads);

                numberOfDownloads++;

                if(data.size() > 0){
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
        }, 0);
    }

    private ArrayAdapter<String> adapter;

    // метод создание списка
    public void createdList(){
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }
}
