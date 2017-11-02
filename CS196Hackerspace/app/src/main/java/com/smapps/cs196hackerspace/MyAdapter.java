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
    private ArrayList<Person> data;
    private LayoutInflater inflater;

    public MyAdapter(Context mContext, ArrayList<Person> items){
        data = items;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        Person currentPerson = (Person) getItem(position);
        name.setText(currentPerson.getName());
        phone.setText(currentPerson.getPhone());
        email.setText(currentPerson.getEmail());
        address.setText(currentPerson.getAddress());

        return myView;
    }
}
