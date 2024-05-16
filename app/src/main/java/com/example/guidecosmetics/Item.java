package com.example.guidecosmetics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Item extends AppCompatActivity {
    SQLiteDatabase db;
    ListView itemList;
    CosmeticAdapterItem cosmeticAdapterItem;
    ArrayList<Cosmetics_item> cosmetics_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        itemList=findViewById(R.id.itemList);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Item.this, InfoActivity.class);
                //getData(); ((TextView) convertView.findViewById(R.id.idView)).setText(String.valueOf(cosmetics_items.id));
                TextView tv1 = view.findViewById(R.id.idView);
                intent.putExtra("idView", tv1.getText().toString());
               // intent.putExtra("id", id);
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
                     startActivity(new Intent(Item.this,Guide.class));

                     finish();
                }
                if (id==R.id.like){
                    startActivity(new Intent(Item.this,Like.class));  finish();
                }
                if (id==R.id.search_item){
                    startActivity(new Intent(Item.this,Search.class));  finish();
                }
                return false;
            }
        });
    }
    @SuppressLint("Range")
    public void getData(){

        Bundle arguments = getIntent().getExtras();
        String Id_types = arguments.get("id").toString();
        int id_types = Integer.parseInt(Id_types);
        String Id_cat= arguments.get("id_cat").toString();
        int id_cat = Integer.parseInt(Id_cat);

        System.out.println(id_cat);
        db = new DBHelper(this).getReadableDatabase();
       // String sql="SELECT * FROM Items WHERE  Items.id_categories="+id_cat;
        String sql="SELECT Items.*, TypesOfCosmetics.typesOfCosmetics FROM Items " +
               "JOIN TypesOfCosmetics ON Items.id_types = TypesOfCosmetics.id " +
               "WHERE Items.id_categories=" + id_cat;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() <= 0) {
            //
        } else {
            cursor.moveToFirst();
            do {

                cosmetics_item.add(new Cosmetics_item(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getBlob(cursor.getColumnIndex("photo")),
                        cursor.getInt(cursor.getColumnIndex("id_categories")),
                        cursor.getInt(cursor.getColumnIndex("id_types")),
                        cursor.getString(cursor.getColumnIndex("desc")),
                        cursor.getInt(cursor.getColumnIndex("like")),
                                cursor.getString(cursor.getColumnIndex("typesOfCosmetics"))
                )

                );
               // Log.d("CosmeticsData", "Types of Cosmetics: " + cursor.getString(cursor.getColumnIndex("typesOfCosmetics")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    protected void onResume() {
        super.onResume();
        cosmetics_item=new ArrayList<>();
        getData();
        cosmeticAdapterItem = new CosmeticAdapterItem(this,cosmetics_item);
        itemList.setAdapter(cosmeticAdapterItem);
    }

}