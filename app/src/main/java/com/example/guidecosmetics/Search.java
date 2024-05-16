package com.example.guidecosmetics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    SQLiteDatabase db;
    ListView itemList;
    ListView list;
    CosmeticAdapterItem cosmeticAdapterItem;
    SearchView  searchView;
    ArrayList<Cosmetics_item> cosmetics_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id=menuItem.getItemId();

                if (id==R.id.home){
                     startActivity(new Intent(Search.this,Guide.class));

                      finish();
                }
                if (id==R.id.like){
                    startActivity(new Intent(Search.this,Like.class));  finish();
                }
                if (id==R.id.search_item){
                    //startActivity(new Intent(Search.this,Search.class));  finish();
                }
                return false;
            }
        });
        itemList = findViewById(R.id.itemList);
        searchView = findViewById(R.id.searchView);



        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Search.this, InfoActivity.class);
                TextView tv1 = view.findViewById(R.id.idView);
                intent.putExtra("idView", tv1.getText().toString());
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Cosmetics_item> filteredList = new ArrayList<>();
                for (Cosmetics_item item : cosmetics_item) {
                    if (item.name.toLowerCase().contains(s.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                cosmeticAdapterItem = new CosmeticAdapterItem(Search.this, filteredList);
                itemList.setAdapter(cosmeticAdapterItem);
                return false;

            }
        });
    }

    @SuppressLint("Range")
    public void getData(){

        db = new DBHelper(this).getReadableDatabase();

        String sql="SELECT Items.*, TypesOfCosmetics.typesOfCosmetics FROM Items " +
                "JOIN TypesOfCosmetics ON Items.id_types = TypesOfCosmetics.id ";


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