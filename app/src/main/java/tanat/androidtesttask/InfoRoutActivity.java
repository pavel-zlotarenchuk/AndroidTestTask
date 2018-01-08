package tanat.androidtesttask;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoRoutActivity extends AppCompatActivity {

    static String data;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rout);

        //выгружаем позицию из интента
        Intent intent = getIntent();
        int route = intent.getIntExtra("position", 0);

        //распарсиваем нужный нам елемент
        data = new JSONFile().examineJSONString(route);

        //инициализируем фрагмент и фрагмент-менеджер
        FragmentManager fragmentManager = getSupportFragmentManager();
        InfoRoutFragment fragmentInfo = (InfoRoutFragment) fragmentManager.findFragmentById(R.id.fragmentInfo);

        //передаем результат в фрагмент
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragmentInfo.setArguments(bundle);
    }
}
