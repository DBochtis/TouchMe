package com.example.dimitris.touchmedemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout buttonRel, lvl1Rel, lvl2Rel, lvl3Rel, stopRel;
    private boolean allowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRel = (RelativeLayout) findViewById(R.id.buttonRel);
        lvl1Rel = (RelativeLayout) findViewById(R.id.lvl1Rel);
        lvl2Rel = (RelativeLayout) findViewById(R.id.lvl2Rel);
        lvl3Rel = (RelativeLayout) findViewById(R.id.lvl3Rel);
        stopRel = (RelativeLayout) findViewById(R.id.stopRel);

        lvl1Rel.setOnClickListener(this);
        lvl2Rel.setOnClickListener(this);
        lvl3Rel.setOnClickListener(this);
        stopRel.setOnClickListener(this);

        buttonRel.setOnClickListener(this);
    }


    final int[] drawablearray = new int[]{R.drawable.red_button, R.drawable.red_button, R.drawable.red_button, R.drawable.red_button,
            R.drawable.red_button, R.drawable.green_button, R.drawable.green_button, R.drawable.green_button, R.drawable.bonus_button};

    private void changeBackground(final int time) {
        if (allowed) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Random r = new Random();
                    int tmp = r.nextInt(9);
                    changeBackgroundColor(tmp);
                    changeBackground(time);
                }
            }, time);
        }
    }

    private void changeBackgroundColor(int position) {
        buttonRel.setBackground(getDrawable(drawablearray[position]));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lvl1Rel:
                allowed = true;
                changeBackground(800);
                break;
            case R.id.lvl2Rel:
                allowed = true;
                changeBackground(500);
                break;
            case R.id.lvl3Rel:
                allowed = true;
                changeBackground(300);
                break;
            case R.id.stopRel:
                allowed = false;
                break;
            case R.id.buttonRel:
                if (buttonRel.getBackground().getConstantState().equals(getDrawable(R.drawable.green_button).getConstantState())) {
                    Toast.makeText(getApplicationContext(), "green", Toast.LENGTH_SHORT).show();
                } else if (buttonRel.getBackground().getConstantState().equals(getDrawable(R.drawable.red_button).getConstantState())) {
                    Toast.makeText(getApplicationContext(), "red", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "bonus", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
