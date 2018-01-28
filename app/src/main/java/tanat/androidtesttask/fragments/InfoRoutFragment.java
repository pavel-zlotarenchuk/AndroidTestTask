package tanat.androidtesttask.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tanat.androidtesttask.R;

public class InfoRoutFragment extends Fragment {

    @BindView(R.id.infoTextView) TextView infoTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_rout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        //check for emptiness
        if (getArguments() != null) {
            //add data in TextViev
            String data = getArguments().getString("data");
            infoTextView.setText(data);
        }
    }
}
