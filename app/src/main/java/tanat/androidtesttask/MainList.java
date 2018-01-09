package tanat.androidtesttask;

import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainList extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList data;
    DialogFragment dialogFragment;
    View view;
    TextView textViewError;
    Button refreshButton;
    ArrayAdapter<String> adapter;
    LinearLayout standartLayout;
    LinearLayout errorLayout;

    //подключаем мой фрагмент
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, null);

        dialogFragment = new MyDialog();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        standartLayout = (LinearLayout) view.findViewById(R.id.standart_layout);
        errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);

        textViewError = (TextView) view.findViewById(R.id.textViewError);
        refreshButton = (Button) view.findViewById(R.id.refreshButton);

        //кнопка для обновления в случае ошибки
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){



 //       data = new MainActivity().demo();

/*        if (data.get(0).toString().equals("false")){
            textViewError.setText(data.get(1).toString());
            data = null;
        } else {
            //создаем лист фрагментов
            adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, data);
            setListAdapter(adapter);
        }*/
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {

        onRefresh();
        super.onStart();
    }

    public void onResume() {

        super.onResume();
    }

    //вешаем слушатель на нажатие итема фрагмента
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //передаем позицию елемента в второе активити
        //создаем интент
        Intent intent = new Intent(MainList.this.getContext(), InfoRoutActivity.class);
        //записываем в него ключ и позицию
        intent.putExtra("position", position);
        //передаем
        startActivity(intent);
    }

    //свайп вниз для обновления
    @Override
    public void onRefresh() {
        /* есть две реализации диалога прогреса,
         * одна представлена возможностями класса  SwipeRefreshLayout, являеться стандартной
         * вторая создана с помощью DialogFragment*/

        // начинаем показывать прогресс
        mSwipeRefreshLayout.setRefreshing(true);

         /*либо используем наш класс, для этого нужно его раскоментить и заменить true на false в
         * строке выше 'mSwipeRefreshLayout.setRefreshing(false);' */

    //    dialogFragment.show(getFragmentManager(), "");

        // вызываем загрузку данных
        data = new MainActivity().demo();

        if(data.size() > 0){
            // данные для списка есть
            // теперь проверим правильно ли была выполнена сетевая операция
            if(data.get(0).toString().equals("false")){
                // сетевая операция не прошла
                // меняем видимость layout так, что б не было видно рабочего layout и был виден
                // layout ошибки и вводим текст ошибки в textView
                standartLayout.setVisibility(View.INVISIBLE);
                errorLayout.setVisibility(View.VISIBLE);
                textViewError.setText(data.get(1).toString());
            } else {
                //если сетевая операция прошла успешно
                errorLayout.setVisibility(View.INVISIBLE);
                standartLayout.setVisibility(View.VISIBLE);
                // создаем список
                createdList();
            }
        } else {
            //если данных списка нету
            errorLayout.setVisibility(View.INVISIBLE);
            standartLayout.setVisibility(View.VISIBLE);
            // создаем список (ListFragment автоматически выведет заданое сообщение)
            createdList();
        }

        // прячем прогресс
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                //либо используем наш класс
        //        dialogFragment.dismiss();
            }
        }, 300);
    }

    // метод создание списка
    public void createdList(){
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

}
