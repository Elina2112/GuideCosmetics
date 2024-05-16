package com.example.guidecosmetics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ScrollView scrollView;
    Button button;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id=menuItem.getItemId();

                if (id==R.id.home){
                     startActivity(new Intent(InfoActivity.this,Guide.class));

                      finish();
                }
                if (id==R.id.like){
                    startActivity(new Intent(InfoActivity.this,Like.class));  finish();
                }
                if (id==R.id.search_item){
                    startActivity(new Intent(InfoActivity.this,Search.class));  finish();
                }
                return false;
            }
        });
        Bundle arguments = getIntent().getExtras();
        //int id = arguments.getInt("id");
        String IdView = arguments.get("idView").toString();

        int idView = Integer.parseInt(IdView);

        System.out.println(idView);

        if (idView!= 0) {
            db = new DBHelper(this).getReadableDatabase();
            String sql = "SELECT * FROM Items WHERE id = " + idView;
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                ((TextView) findViewById(R.id.nameView)).setText(
                        cursor.getString(cursor.getColumnIndex("name")).replaceAll("_", " "));//!!!!
                byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(
                        BitmapFactory.decodeByteArray(photo, 0, photo.length));
                String htmlContent = "<p style='text-align: justify;'>" + cursor.getString(cursor.getColumnIndex("desc")) + "</p>";
                ((WebView) findViewById(R.id.descView)).loadData(
                        htmlContent, "text/html; charset=utf-8", "UTF-8");
                ((TextView) findViewById(R.id.likeText)).setText(
                        cursor.getString(cursor.getColumnIndex("like")));
                String et=String.valueOf(cursor.getString(6));
                System.out.println(et);

                ImageButton btn = (ImageButton)findViewById(R.id.likeView);
               // btn.setImageResource(R.drawable.actions_record);
                if (Integer.parseInt(et)==1) btn.setImageResource(R.drawable.like);
                        else btn.setImageResource(R.drawable.nolike);

                String Et=String.valueOf(cursor.getString(6));
                btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("Click");

                                System.out.println(Et);
                                int et=Integer.parseInt(Et);
                                System.out.println(et);
                                if (et==0) {
                                    btn.setImageResource(R.drawable.like);
                                    et=1;
                                }else
                                {btn.setImageResource(R.drawable.nolike);
                                    et=0;} System.out.println(et);
                               // this.recreate();
                                String strSQL = "UPDATE Items SET like ="+ et+" WHERE id = "+ idView;
                              //  cursor = db.rawQuery("UPDATE Items"
                               //         + " SET like='"+et+"' where id='" + idView + "'",null);
                                db.execSQL(strSQL);db.close();
                               // db.update()
                                finish();
                                 overridePendingTransition(0, 0);
                                 startActivity(getIntent());
                                 overridePendingTransition(0, 0);
                              // startActivity(InfoActivity);
                            }
                        });



            }
            cursor.close();
            //
          //  finish();
        }



    }



}