package tanat.androidtesttask.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tanat.androidtesttask.R;

public class InfoRoutFragment extends Fragment {

    private TextView infoTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_rout, container, false);

        infoTextView = (TextView) rootView.findViewById(R.id.infoTextView);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        //проверяем на пустоту
        if (getArguments() != null) {
            //добавляем данные в TextViev
            String data = getArguments().getString("data");
            infoTextView.setText(data);
        }
    }
}
