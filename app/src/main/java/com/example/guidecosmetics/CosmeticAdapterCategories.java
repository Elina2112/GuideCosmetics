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

public class CosmeticAdapterCategories extends ArrayAdapter<Cosmetics_categories> {

    public CosmeticAdapterCategories(@NonNull Context context, ArrayList<Cosmetics_categories> cosmetics_categories) {
        super(context, R.layout.list_item, cosmetics_categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cosmetics_categories cosmetics_categories = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cat, null);
        }


        ((TextView) convertView.findViewById(R.id.nameView)).setText(String.valueOf(cosmetics_categories.name));
        ((TextView) convertView.findViewById(R.id.id_cat)).setText(String.valueOf(cosmetics_categories.id));
        Bitmap photo = BitmapFactory.decodeByteArray(cosmetics_categories.photo, 0, cosmetics_categories.photo.length);
        ((ImageView) convertView.findViewById(R.id.photoView)).setImageBitmap(photo);


        return convertView;
    }
}