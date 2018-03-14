package tanat.androidtesttask.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tanat.androidtesttask.R;

public class PrivateContentActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_content);

        //You can set the condition for consumption of the purchase or it can be one time fee
        //it depends on your application requirement.
        // but if you to consume a purchase, call the method below
        //String token = customSharedPreference.getPurchaseToken();
        // You can call consume purchase
        //consumePurchaseItem(token)
    }
}