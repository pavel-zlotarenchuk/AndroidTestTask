package tanat.androidtesttask.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import tanat.androidtesttask.R;

public class StartActivity extends Activity {
    private AdView mAdView;

    private Button billingInAppButton;
    private Button volleyButton;
    private Button serviceButton;
    private Button retrofitButton;
    private Button rxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        //AdMob
        MobileAds.initialize(StartActivity.this, "ca-app-pub-3940256099942544~3347511713");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        billingInAppButton = (Button) findViewById(R.id.billingInAppButton);
        volleyButton = (Button) findViewById(R.id.volleyButton);
        serviceButton = (Button) findViewById(R.id.serviceButton);
        retrofitButton = (Button) findViewById(R.id.retrofitButton);
        rxButton = (Button) findViewById(R.id.rxButton);

        billingInAppButton.setOnClickListener(billingInAppClick);
        volleyButton.setOnClickListener(volleyListClick);
        serviceButton.setOnClickListener(serviceListClick);
        retrofitButton.setOnClickListener(retrofitListClick);
        rxButton.setOnClickListener(rxListClick);
    }

    View.OnClickListener billingInAppClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //startActivity(new Intent(StartActivity.this, BillingActivity.class));
        }
    };

    View.OnClickListener volleyListClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(VolleyMainActivity.class);
        }
    };

    View.OnClickListener retrofitListClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(RetrofitActivity.class);
        }
    };

    View.OnClickListener rxListClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(RxJavaActivity.class);
        }
    };

    View.OnClickListener serviceListClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(MainActivity.class);
        }
    };

    private void startActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }
}
