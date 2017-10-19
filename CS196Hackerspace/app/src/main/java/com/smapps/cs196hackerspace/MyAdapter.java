package com.smapps.cs196hackerspace;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sahil on 10/18/2017.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data;
    private LayoutInflater inflater;

    public MyAdapter(Context mContext, ArrayList<String> items){
        context = mContext;
        data = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = inflater.inflate(R.layout.activity_list_view, parent, false);
        TextView text = (TextView) myView.findViewById(R.id.listTextView);
        Log.d("ADAPTER", (String) getItem(position));
        text.setText((String) getItem(position));

        return myView;
    }
}
