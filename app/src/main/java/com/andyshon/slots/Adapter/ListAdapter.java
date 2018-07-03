package com.andyshon.slots.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andyshon.slots.R;

import java.util.List;

/**
 * Created by andyshon on 03.07.18.
 */

public class ListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> combinations;
    private final List<Integer> images;


    public ListAdapter(Activity context, List<String> combinations, List<Integer> images) {
        super(context, R.layout.list_single, combinations);
        this.context = context;
        this.combinations = combinations;
        this.images = images;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = view;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_single, null, true);
        }
        TextView txtTitle = (TextView) rowView.findViewById(R.id.combination);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(combinations.get(position));

        imageView.setImageResource(images.get(position));
        return rowView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
