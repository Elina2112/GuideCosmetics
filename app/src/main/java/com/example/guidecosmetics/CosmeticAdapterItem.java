package com.example.guidecosmetics;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CosmeticAdapterItem extends ArrayAdapter<Cosmetics_item> {

    public CosmeticAdapterItem(@NonNull Context context, ArrayList<Cosmetics_item> cosmetics_items) {
        super(context, R.layout.list_item_item, cosmetics_items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cosmetics_item cosmetics_items = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_item, null);
        }


        ((TextView) convertView.findViewById(R.id.nameView)).setText(String.valueOf(cosmetics_items.name).replaceAll("_", "\n").replaceAll("_", "\n"));
        ((TextView) convertView.findViewById(R.id.idView)).setText(String.valueOf(cosmetics_items.id));
        ((TextView) convertView.findViewById(R.id.likeText)).setText(String.valueOf(cosmetics_items.like));
        String et=String.valueOf(cosmetics_items.like);
       // imageView.setImageResource(R.drawable.icecream);
        if (Integer.parseInt(et)==1) ((ImageView)convertView.findViewById(R.id.likeView)).setImageResource(R.drawable.like);
        else ((ImageView)convertView.findViewById(R.id.likeView)).setImageResource(R.drawable.nolike);
        //((TextView) convertView.findViewById(R.id.descView)).setText(String.valueOf(cosmetics_items.desc));
        Bitmap photo = BitmapFactory.decodeByteArray(cosmetics_items.photo, 0, cosmetics_items.photo.length);
        ((ImageView) convertView.findViewById(R.id.photoView)).setImageBitmap(photo);
       // ((TextView) convertView.findViewById(R.id.nameTypes)).setText(String.valueOf(cosmetics_items.i));
        ((TextView) convertView.findViewById(R.id.nameTypes)).setText(cosmetics_items.getTypesOfCosmetics());
        return convertView;
    }
}