package com.example.goskate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        this.mContext = mContext;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    // set text to the view
    private void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView textViewTitle = (TextView) view.findViewById(R.id.title);
        if (!title.equals("")){
            textViewTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView textViewSnippet = (TextView) view.findViewById(R.id.snippet);
        if (!snippet.equals("")){
            textViewSnippet.setText(snippet);
        }
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
