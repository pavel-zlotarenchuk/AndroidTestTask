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
        Intent intent = getIntent();
        int route = intent.getIntExtra("position", 0);

        //parse the required element to us
        data = new JSONParsing().examineJSONString(route);

        //initialize the fragment and the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        InfoRoutFragment fragmentInfo = (InfoRoutFragment) fragmentManager.findFragmentById(R.id.fragmentInfo);

        //pass the result to the fragment
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragmentInfo.setArguments(bundle);
    }
}
