package tanat.androidtesttask;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InfoRoutFragment extends Fragment {

    TextView textView;

    public InfoRoutFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_rout, container, false);

        textView = (TextView) view.findViewById(R.id.infoTextView);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        //проверяем на пустоту
        if (getArguments() != null) {
            //добавляем данные в TextViev
            String data = getArguments().getString("data");
            textView.setText(data);
        }
    }
}
