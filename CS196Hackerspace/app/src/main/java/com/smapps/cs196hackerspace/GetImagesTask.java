package com.smapps.cs196hackerspace;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Sahil on 12/3/2017.
 */


public class GetImagesTask extends AsyncTask<String, Integer, ArrayList<Bitmap>> {
    private Activity activity;
    private ProgressBar progressBar;
    private ListView asyncImagesListView;

    public GetImagesTask(Activity activity) {
        this.activity = activity;
        progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        asyncImagesListView = (ListView) activity.findViewById(R.id.asyncImagesListView);

    }

    @Override
    protected ArrayList<Bitmap> doInBackground(String... urls) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        int count = urls.length;
        for (int i = 0; i < count; i++) {
               /*
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               */
            Bitmap bmp;
            try {
                InputStream in = new java.net.URL(urls[i]).openStream();
                bmp = BitmapFactory.decodeStream(in);
                bitmaps.add(bmp);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                e.printStackTrace();
            }
            Log.d("TEST", "" + count + "    " + i);
            publishProgress((int) ((i / (float) count) * 100));
        }

        return bitmaps;
    }

    protected void onProgressUpdate(Integer... progress) {
        Log.d("PROGRESS", "" + progress[0]);
        progressBar.setProgress(progress[0]);
    }

    protected void onPostExecute(ArrayList<Bitmap> result) {
        progressBar.setProgress(100);
        AsyncImageAdapter adapter = new AsyncImageAdapter(activity, result);
        asyncImagesListView.setAdapter(adapter);
    }

}

