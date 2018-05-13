package com.recepcil.catchthekenny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewScoreActivity extends AppCompatActivity {

    static String score="";
    EditText edtName;
    TextView txtScore;
    TextView txtName;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_score);

        txtScore = (TextView) findViewById(R.id.txtScore);
        edtName = (EditText) findViewById(R.id.edtName);
        txtName = (TextView) findViewById(R.id.txtName);

        Intent intent = getIntent();
        score = intent.getStringExtra("score");
        txtScore.setText(score);

    }

    public void btnKaydet(View view){

        if(count == 0){
            edtName.setVisibility(View.INVISIBLE);
            txtName.setText(edtName.getText());
            txtName.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getApplicationContext(),ScoreActivity.class);
            intent.putExtra("info","new");
            intent.putExtra("score",score);
            intent.putExtra("name",(edtName.getText().toString()));
            startActivity(intent);
            count++;
        }
    }


}
