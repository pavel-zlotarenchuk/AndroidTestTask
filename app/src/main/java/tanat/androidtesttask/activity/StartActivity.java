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

        billingInAppButton.setOnClickListener(billingInAppClick);
        volleyButton.setOnClickListener(volleyListClick);
        serviceButton.setOnClickListener(serviceListClick);
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
            startActivity(new Intent(StartActivity.this, VolleyMainActivity.class));
        }
    };

    View.OnClickListener serviceListClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
    };
}
