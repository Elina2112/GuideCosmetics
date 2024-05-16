package com.example.guidecosmetics;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cosmetics_categories extends AppCompatActivity {


    String name,desc;
    int id,id_types;
    byte[] photo;

    public Cosmetics_categories(int id, String name, byte[] photo,int id_types,String desc)
    {
        this.id=id;
        this.name=name;
        this.photo=photo;
        this.id_types=id_types;
        this.desc=desc;

    }
}