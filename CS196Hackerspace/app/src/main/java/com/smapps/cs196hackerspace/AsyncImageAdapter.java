package com.smapps.cs196hackerspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Sahil on 12/3/2017.
 */

public class AsyncImageAdapter extends BaseAdapter {
    ArrayList<Bitmap> data;
    Context context;
    LayoutInflater inflater;

    public AsyncImageAdapter(Context c, ArrayList<Bitmap> d) {
        context = c;
        data = d;
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
        View myView = inflater.inflate(R.layout.image_item, parent, false);
        ImageView imageView = (ImageView) myView.findViewById(R.id.asyncImageView);
        imageView.setImageBitmap((Bitmap) getItem(position));
        return myView;
    }
}

