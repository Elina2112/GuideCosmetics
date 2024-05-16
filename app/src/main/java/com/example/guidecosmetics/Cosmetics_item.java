package com.example.guidecosmetics;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cosmetics_item extends AppCompatActivity {
    String typesOfCosmetics;
    String name,desc;
    int id,id_types,id_categories,like;
    byte[] photo;

    public Cosmetics_item(int id, String name, byte[] photo,int id_categories,int id_types,String desc,int like,String typesOfCosmetics)
    {
        this.id=id;
        this.name=name;
        this.photo=photo;
        this.id_types=id_types;
        this.id_categories=id_categories;
        this.desc=desc;
        this.like=like;
        this.typesOfCosmetics=typesOfCosmetics;
    }
    public String getTypesOfCosmetics() {
        return typesOfCosmetics;
    }
}