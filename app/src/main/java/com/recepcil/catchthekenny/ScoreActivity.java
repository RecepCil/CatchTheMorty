package com.recepcil.catchthekenny;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    static ListView lvHighScore;
    public static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lvHighScore = findViewById(R.id.lvHighScore);
        final ArrayList<String> nameArray = new ArrayList<String>();

        //ArrayList'i ekrana aktar.
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameArray);
        lvHighScore.setAdapter(arrayAdapter);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        /*YENİ SCORE GİR*/

        if(info.equalsIgnoreCase("new")){

            String scoreStr = intent.getStringExtra("score");
          //  Integer score = Integer.parseInt(scoreStr);
            String name = intent.getStringExtra("name");



            //Veritabanını ve table'ı aç(veya yarat)
            MainActivity.myDatabase = this.openOrCreateDatabase("CatchTheMorty",MODE_PRIVATE,null);
            MainActivity.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS highscores (nick VARCHAR,score INT)");
            MainActivity.myDatabase.execSQL("DELETE FROM highscores");


            try{
            //veritabanına string ve blob değer kaydet
            String sqlString = "INSERT INTO highscores (nick,score) VALUES (?,? )";
            SQLiteStatement statement = MainActivity.myDatabase.compileStatement(sqlString);
            statement.bindString(1,name);
            statement.bindLong(2,Long.parseLong(scoreStr));
            statement.execute();

            }catch (Exception e){
                Toast.makeText(this,e.toString()+name+scoreStr,Toast.LENGTH_LONG).show();
            }

        }

        /*SCORE AYARLARI*/
        try{

            Cursor cursor = MainActivity.myDatabase.rawQuery("SELECT * FROM highscores",null);

            int count =0;
            cursor.moveToFirst(); //Cursor ilk entry'i göstersin
            while(cursor!=null && count <4){

                //Veritabanındaki "name" columnua ait olan değerleri çek ve nameArray dizisine ata
                nameArray.add(
                        //(cursor.getPosition()+1)+"   "+
                        (cursor.getString(cursor.getColumnIndex("nick"))) +"   " +
                                cursor.getString(cursor.getColumnIndex("score")));


                cursor.moveToNext();
                //ArrayAdapter'ı güncelle, yeni veri geldiyse ekrana yansıt
                arrayAdapter.notifyDataSetChanged();

                count++;
            }
        }
        catch (Exception e){}
    }
}

