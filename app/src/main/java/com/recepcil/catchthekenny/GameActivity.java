package com.recepcil.catchthekenny;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView txtTime;
    TextView txtScore;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;

    static Integer score=0;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView8 = (ImageView) findViewById(R.id.imageView8);
        imageArray = new ImageView[]{imageView,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8};

        waitOrStart();

    }

    public void waitOrStart(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle("Click the OK button to start.");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gameIsOn();
            }
        });

        alert.show();


    }

    public void gameIsOn(){

        txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setText("Score: 0");

        hideImages();

        txtTime = (TextView) findViewById(R.id.txtTime);

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                txtTime.setText("Time: "+(l/1000));
            }

            @Override
            public void onFinish() {
                txtTime.setText("Time's Done! ");

                handler.removeCallbacks(runnable);

                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                /*SCORE KONTROLU*/
                try{

                    Cursor cursor = MainActivity.myDatabase.rawQuery("SELECT * FROM highscores",null);

                    if(cursor!=null){

                        cursor.moveToFirst(); //Cursor ilk entry'i göstersin.
                     //   Toast.makeText(getApplicationContext(), "Score"+score ,Toast.LENGTH_LONG).show();
                        if(Integer.parseInt(cursor.getString(cursor.getColumnIndex("score"))) < score) {

                            /*SCORE U KAYDET*/

                            Intent intent = new Intent(getApplicationContext(),NewScoreActivity.class);
                            intent.putExtra("score",score.toString());
                            startActivity(intent);

                            ///////////////////
                        }
                    }else{

                    }
                }
                catch (Exception e){
                             MainActivity.myDatabase.execSQL("DELETE FROM highscores");
                            MainActivity.myDatabase.execSQL("INSERT INTO highscores (nick,score) VALUES ('NoOne',0 )");
                }


                /*end of SCORE KONTROLU*/



                waitOrStart();
            }
        }.start();

        score = 0;

    }

    public void gameIsOnLevel2(){

        hideImages();

        CountDownTimer cdt = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                txtTime.setText("Time: "+(l/1000));
            }

            @Override
            public void onFinish() {
                txtTime.setText("Time's Done! ");

                handler.removeCallbacks(runnable);

                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                /*SCORE KONTROLU*/
                try{

                    Cursor cursor = MainActivity.myDatabase.rawQuery("SELECT * FROM highscores",null);

                    if(cursor!=null){

                        cursor.moveToFirst(); //Cursor ilk entry'i göstersin.
                        //   Toast.makeText(getApplicationContext(), "Score"+score ,Toast.LENGTH_LONG).show();
                        if(Integer.parseInt(cursor.getString(cursor.getColumnIndex("score"))) < score) {

                            /*SCORE U KAYDET*/

                            Intent intent = new Intent(getApplicationContext(),NewScoreActivity.class);
                            intent.putExtra("score",score.toString());
                            startActivity(intent);

                            ///////////////////
                        }
                    }else{

                    }
                }
                catch (Exception e){
                    MainActivity.myDatabase.execSQL("DELETE FROM highscores");
                    MainActivity.myDatabase.execSQL("INSERT INTO highscores (nick,score) VALUES ('NoOne',0 )");
                }


                /*end of SCORE KONTROLU*/



                //waitOrStart();
            }
        }.start();

        score = 0;

    }

    public void increaseScore(View view){

        txtScore = (TextView) findViewById(R.id.txtScore);

        score++;

        txtScore.setText("Score:" +score);

        //if(score==15) gameIsOnLevel2();
    }

    public void hideImages(){

        handler =new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                Random r = new Random(); int rand = r.nextInt(9);
                imageArray[rand].setVisibility(View.VISIBLE);
                handler.postDelayed(this,350);
            }
        };

        handler.post(runnable);


    }
}
