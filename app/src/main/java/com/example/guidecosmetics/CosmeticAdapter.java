package com.example.guidecosmetics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CosmeticAdapter extends ArrayAdapter<Cosmetics> {
    public CosmeticAdapter(@NonNull Context context, ArrayList<Cosmetics> cosmetics) {
        super(context, R.layout.list_item, cosmetics);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cosmetics cosmetic = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }


        ((TextView) convertView.findViewById(R.id.nameView)).setText(String.valueOf(cosmetic.typesOfCosmetics));


        Bitmap photo = BitmapFactory.decodeByteArray(cosmetic.photo, 0, cosmetic.photo.length);
        ((ImageView) convertView.findViewById(R.id.photoView)).setImageBitmap(photo);


        return convertView;
    }
}

