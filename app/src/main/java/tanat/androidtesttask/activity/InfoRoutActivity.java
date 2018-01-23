package tanat.androidtesttask.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tanat.androidtesttask.utils.JSONParsing;
import tanat.androidtesttask.R;
import tanat.androidtesttask.fragments.InfoRoutFragment;

public class InfoRoutActivity extends AppCompatActivity {

    private static String data;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rout);

        //unload a position from intent
        //выгружаем позицию из интента
        Intent intent = getIntent();
        int route = intent.getIntExtra("position", 0);

        //parse the required element to us
        //распарсиваем нужный нам елемент
        data = new JSONParsing().examineJSONString(route);

        //инициализируем фрагмент и фрагмент-менеджер
        FragmentManager fragmentManager = getSupportFragmentManager();
        InfoRoutFragment fragmentInfo = (InfoRoutFragment) fragmentManager.findFragmentById(R.id.fragmentInfo);

        //передаем результат в фрагмент
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragmentInfo.setArguments(bundle);
    }
}
