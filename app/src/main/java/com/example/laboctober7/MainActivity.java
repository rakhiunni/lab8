package com.example.laboctober7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int incrementValue = 1;

    private int score1 = 0;
    private int score2 = 0;

    TextView score1_tv, score2_tv;
    Button score1_minus_bt, score2_minus_bt, score1_plus_bt, score2_plus_bt;

    RadioGroup incrementSelector;
    RadioButton incrementBy1, incrementBy5, incrementBy10;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score1_tv = findViewById(R.id.tv_score1);
        score2_tv = findViewById(R.id.tv_score2);

        score1_minus_bt = findViewById(R.id.btn_score1Minus);
        score1_plus_bt = findViewById(R.id.btn_score1Plus);
        score2_plus_bt = findViewById(R.id.btn_score2Plus);
        score2_minus_bt = findViewById(R.id.btn_score2Minus);

        incrementSelector = findViewById(R.id.radio_group);
        incrementBy1 = findViewById(R.id.radio_1);
        incrementBy5 = findViewById(R.id.radio_5);
        incrementBy10 = findViewById(R.id.radio_10);

        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("1")){
           score1_tv.setText(Integer.toString(sharedPreferences.getInt("1", 0)));
           score1 = sharedPreferences.getInt("1", 0);
        }
        if(sharedPreferences.contains("2")){
            score2_tv.setText(Integer.toString(sharedPreferences.getInt("2", 0)));
            score2 = sharedPreferences.getInt("2", 0);
        }
        if (sharedPreferences.contains("radio")) {
            RadioButton radioButton;
            incrementValue = sharedPreferences.getInt("radio", 1);
            if (sharedPreferences.getInt("radio", 1) == 1) {
                radioButton = findViewById(R.id.radio_1);
                radioButton.setChecked(true);
            } else if (sharedPreferences.getInt("radio", 1) == 5) {
                radioButton = findViewById(R.id.radio_5);
                radioButton.setChecked(true);
            } else {
                radioButton = findViewById(R.id.radio_10);
                radioButton.setChecked(true);
            }
        }


        incrementSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (checkedId) {
                    case R.id.radio_1:
                        incrementValue = 1;
                        editor.putInt("radio", 1);
                        break;
                    case R.id.radio_5:
                        incrementValue = 5;
                        editor.putInt("radio", 5);
                        break;
                    case R.id.radio_10:
                        incrementValue = 10;
                        editor.putInt("radio", 10);
                        break;
                }
                editor.apply();
            }
        });

        score1_minus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score1 - incrementValue >= 0) {
                    score1 = score1 - incrementValue;

                    score1_tv.setText(Integer.toString(score1));
                    updateInMemory("1");
                }
            }
        });

        score1_plus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score1= score1 + incrementValue;

                    score1_tv.setText(Integer.toString(score1));
                    updateInMemory("1");
            }
        });

        score2_plus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    score2 = score2 + incrementValue;

                    score2_tv.setText(Integer.toString(score2));
                updateInMemory("2");

            }
        });

        score2_minus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score2 - incrementValue >= 0) {
                    score2 = score2 - incrementValue;

                    score2_tv.setText(Integer.toString(score2));
                    updateInMemory("2");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator_menu = getMenuInflater();
        inflator_menu.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            return true;


            case R.id.info:
                Toast.makeText(this, "About the app", Toast.LENGTH_SHORT).show();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    void updateInMemory(String scoreNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (scoreNumber.equals("1")) {
            editor.putInt("1", score1);
        } else {
            editor.putInt("2", score2);
        }
        editor.apply();
    }


}

