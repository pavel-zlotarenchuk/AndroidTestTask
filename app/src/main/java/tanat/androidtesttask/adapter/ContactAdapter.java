package tanat.androidtesttask.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tanat.androidtesttask.R;
import tanat.androidtesttask.model.Data;

/**
 * Created by mac on 2/27/18.
 */

public class ContactAdapter extends ArrayAdapter<Data> {

    List<Data> dataList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public ContactAdapter(Context context, List<Data> dataList) {
        super(context, 0, dataList);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public Data getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((ConstraintLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Data item = getItem(position);

        vh.idTextView.setText(String.valueOf(item.getId()));
        vh.fromDateTextView.setText(item.getFromDate());
        vh.fromTimeTextView.setText(item.getFromTime());
        vh.fromDateTextView.setText(item.getFromDate());
        vh.fromInfoTextView.setText(item.getFromInfo());
        vh.fromPriceTextView.setText(String.valueOf(item.getPrice()));

        return vh.rootView;
    }

    private static class ViewHolder {
        public ConstraintLayout rootView;
        public TextView idTextView;
        public TextView fromDateTextView;
        public TextView fromTimeTextView;
        public TextView fromInfoTextView;
        public TextView fromPriceTextView;

        private ViewHolder(ConstraintLayout rootView, TextView idTextView, TextView fromDateTextView, TextView fromTimeTextView, TextView fromInfoTextView, TextView fromPriceTextView) {
            this.rootView = rootView;
            this.idTextView = idTextView;
            this.fromDateTextView = fromDateTextView;
            this.fromTimeTextView = fromTimeTextView;
            this.fromInfoTextView = fromInfoTextView;
            this.fromPriceTextView = fromPriceTextView;
        }

        public static ViewHolder create(ConstraintLayout rootView) {
            TextView idTextView = (TextView) rootView.findViewById(R.id.idTextView);
            TextView fromDateTextView = (TextView) rootView.findViewById(R.id.fromDateTextView);
            TextView fromTimeTextView = (TextView) rootView.findViewById(R.id.fromTimeTextView);
            TextView fromInfoTextView = (TextView) rootView.findViewById(R.id.fromInfoTextView);
            TextView fromPriceTextView = (TextView) rootView.findViewById(R.id.fromPriceTextView);
            return new ViewHolder(rootView, idTextView, fromDateTextView, fromTimeTextView, fromInfoTextView, fromPriceTextView);
        }

    }

}