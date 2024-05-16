package com.example.guidecosmetics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class More extends AppCompatActivity {
    SQLiteDatabase db;
    ListView moreList;
    CosmeticAdapterCategories cosmeticAdapterCategories;
    ArrayList<Cosmetics_categories> cosmetics_categories;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_more);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        moreList=findViewById(R.id.moreList);

        moreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(More.this, Item.class);
                TextView tv1 = view.findViewById(R.id.nameView);
                TextView tv2 = view.findViewById(R.id.id_cat);
                intent.putExtra("name", tv1.getText().toString());
                intent.putExtra("id_cat", tv2.getText().toString());
                System.out.println(tv1);
                System.out.println(tv2);
                intent.putExtra("id", id);
                TextView tv3 = view.findViewById(R.id.nameTypes);
                intent.putExtra("nameTypes", tv3.getText().toString());
                System.out.println(tv3.getText().toString());
             //   Bundle arguments = getIntent().getExtras();
                //int id = arguments.getInt("id");
               // String NameTypes = arguments.get("nameTypes").toString();

             //   intent.putExtra("nameTypes", NameTypes);
             //   System.out.println(NameTypes);
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
                     startActivity(new Intent(More.this,Guide.class));

                      finish();
                }
                if (id==R.id.like){
                    startActivity(new Intent(More.this,Like.class));  finish();
                }
                if (id==R.id.search_item){
                    startActivity(new Intent(More.this,Search.class));  finish();
                }
                return false;
            }
        });

    }
    @SuppressLint("Range")
    public void getData(){

        Bundle arguments = getIntent().getExtras();
        //int id = arguments.getInt("id");
        String Id_types = arguments.get("id").toString();
        int id_types = Integer.parseInt(Id_types);
        db = new DBHelper(this).getReadableDatabase();

        String sql="SELECT * FROM Categories WHERE id_types="+(id_types+1);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() <= 0) {
            //
        } else {
            cursor.moveToFirst();
            do {
                cosmetics_categories.add(new Cosmetics_categories(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getBlob(cursor.getColumnIndex("image")),
                        cursor.getInt(cursor.getColumnIndex("id_types")),
                        cursor.getString(cursor.getColumnIndex("desc"))
                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    protected void onResume() {
        super.onResume();
        cosmetics_categories=new ArrayList<>();
        getData();
        cosmeticAdapterCategories = new CosmeticAdapterCategories(this, cosmetics_categories);
        moreList.setAdapter(cosmeticAdapterCategories);
    }
}