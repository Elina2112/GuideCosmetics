package com.example.guidecosmetics;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cosmetics extends AppCompatActivity {

   // public byte[] photo;
    String typesOfCosmetics;
   int id;
   byte[] photo;

   public Cosmetics(int id, String typesOfCosmetics, byte[] photo)
   {
       this.id=id;
       this.typesOfCosmetics=typesOfCosmetics;
       this.photo=photo;
   }
}