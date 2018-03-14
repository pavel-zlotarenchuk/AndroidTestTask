package tanat.androidtesttask.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import tanat.androidtesttask.R;
import tanat.androidtesttask.api.ApiManager;
import tanat.androidtesttask.errorreporter.ErrorManager;
import tanat.androidtesttask.utils.JSONParsing;

public class RxJavaActivity extends AppCompatActivity {

    private ListView listView;
    private LinearLayout listLayout;
    private LinearLayout errorLayout;
    private TextView errorTextView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_java_activity);

        listLayout = findViewById(R.id.listLayout);
        errorLayout = findViewById(R.id.errorLayout);
        listView = findViewById(R.id.listView);
        errorTextView = findViewById(R.id.errorTextView);

        filleList();
    }

    private void filleList() {
/*        Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws IOException {
                URL url = new URL("http://projects.gmoby.org/web/index.php/api/trips?from_date=2016-01-01&to_date=2018-03-01");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return new JSONParsing().examineJSONDemoString(buffer.toString());
            }
        })*/
                ApiManager.loadObservable().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> dataList) {
                        visibleListLayout();
                        hideErrorLayout();
                        adapter = new ArrayAdapter<String>(RxJavaActivity.this, android.R.layout.simple_list_item_1, dataList);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideListLayout();
                        visibleErrorLayout();
                        errorTextView.setText(new ErrorManager(RxJavaActivity.this).errorType(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void visibleListLayout(){
        listLayout.setVisibility(View.VISIBLE);
    }

    public void hideListLayout(){
        listLayout.setVisibility(View.GONE);
    }

    public void visibleErrorLayout(){
        errorLayout.setVisibility(View.VISIBLE);
    }

    public void hideErrorLayout(){
        errorLayout.setVisibility(View.GONE);
    }
}
