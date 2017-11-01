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
        View myView = inflater.inflate(R.layout.list_item, parent, false);
        TextView name = (TextView) myView.findViewById(R.id.nameText);
        TextView phone = (TextView) myView.findViewById(R.id.phoneText);
        TextView email = (TextView) myView.findViewById(R.id.emailText);
        TextView address = (TextView) myView.findViewById(R.id.addressText);
        Log.d("ADAPTER", (String) getItem(position));
        name.setText((String) getItem(position));
        phone.setText((String) getItem(position));
        email.setText((String) getItem(position));
        address.setText((String) getItem(position));

        return myView;
    }
}
