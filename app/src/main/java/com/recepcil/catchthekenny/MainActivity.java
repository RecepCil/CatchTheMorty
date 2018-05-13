package com.recepcil.catchthekenny;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            //Burda database
            myDatabase = this.openOrCreateDatabase("CatchTheMorty",MODE_PRIVATE,null);
            //Burda Table yaratıldı
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                    "highscores (nick VARCHAR,score INT)");

           //         myDatabase.execSQL("DELETE FROM highscores");
           //          myDatabase.execSQL("INSERT INTO highscores (nick,score) VALUES ('NoOne',0 )");

        }
        catch(Exception e){}
    }

    public void btnStart(View view){

        Intent intent = new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);

    }

    public void btnHighScores(View view){

        Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
        intent.putExtra("info","old");
        startActivity(intent);

    }

    public void btnQuit(View view){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Are you SURE to go?");

        alert.setPositiveButton("Yes, PLEASE!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
                System.exit(0);
            }
        });
        alert.setNegativeButton("Absolutely, NOT!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();




    }

}
