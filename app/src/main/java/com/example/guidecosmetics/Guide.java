package com.example.guidecosmetics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Guide extends AppCompatActivity {
    ListView cosmeticsList;
    SQLiteDatabase db;
    CosmeticAdapter cosmeticAdapter;

    ArrayList<Cosmetics> cosmetics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guide);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cosmeticsList=findViewById(R.id.cosmeticsList);
        cosmeticsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Guide.this, More.class);
                intent.putExtra("id", id);
               // TextView tv1= view.findViewById(R.id.nameView);
                //intent.putExtra("nameTypes", tv1.getText().toString());
                System.out.println(id);
              //  System.out.println( tv1.getText().toString());
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id=menuItem.getItemId();

                if (id==R.id.home){
                   // startActivity(new Intent(Guide.this,Guide.class));

                  //  finish();
                }
                if (id==R.id.like){
                    startActivity(new Intent(Guide.this,Like.class));  finish();
                }
                if (id==R.id.search_item){
                    startActivity(new Intent(Guide.this,Search.class));  finish();
                }
                return false;
            }
        });
    }



    @SuppressLint("Range")
    public void getData(){
        db=new DBHelper(this).getReadableDatabase();

        String sql="SELECT * FROM TypesOfCosmetics";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() <= 0) {
            //
        } else {
            cursor.moveToFirst();
            do {
                cosmetics.add(new Cosmetics(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("typesOfCosmetics")),
                        cursor.getBlob(cursor.getColumnIndex("photo"))
                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }//TypesOfCosmetics
    @Override
    protected void onResume() {
        super.onResume();
        cosmetics=new ArrayList<>();
        getData();
        cosmeticAdapter = new CosmeticAdapter(this, cosmetics);
        cosmeticsList.setAdapter(cosmeticAdapter);
    }

}