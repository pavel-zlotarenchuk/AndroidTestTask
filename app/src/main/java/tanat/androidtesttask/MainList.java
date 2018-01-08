package tanat.androidtesttask;

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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainList extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        //загружаем список
        ArrayList data = new MainActivity().demo();

        //создаем лист фрагментов
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    //подключаем мой фрагмент
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null);
//        view.setOnTouchListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    //вешаем слушатель на нажатие фрагмента
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

    @Override
    public void onRefresh() {
        // говорим о том, что собираемся начать
        Toast.makeText(getActivity(), "Обновляем", Toast.LENGTH_SHORT).show();
        // начинаем показывать прогресс
        mSwipeRefreshLayout.setRefreshing(true);
        // ждем 3 секунды и прячем прогресс
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                // говорим о том, что собираемся закончить
                Toast.makeText(getActivity(),"обновили", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }
}
