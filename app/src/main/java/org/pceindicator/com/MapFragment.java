package org.pceindicator.com;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ABHIJAY on 3/13/2018.
 */

public class MapFragment extends Fragment {

    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        View view = inflater.inflate(R.layout.map_fragment, viewGroup, false);
        imageView = view.findViewById(R.id.output);
        Picasso.get().load(R.drawable.map).resize(width-256,height-256).into(imageView);
        return view;
    }

}
