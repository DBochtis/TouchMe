package com.example.dimitris.touchmedemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimitris.touchmedemo.R;
import com.example.dimitris.touchmedemo.helper.TypefacesHelper;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends FragmentActivity implements View.OnClickListener {

    private boolean allowed = false;
    private int counter = 0;
    private CountDownTimer timer;

    @BindView(R.id.buttonRel)
    RelativeLayout buttonRel;
    @BindView(R.id.startRel)
    RelativeLayout startRel;
    @BindView(R.id.timeTxtView)
    TextView timeTxtView;
    @BindView(R.id.scoreTxtView)
    TextView scoreTxtView;
    @BindView(R.id.levelTxtView)
    TextView levelTxtView;
    @BindView(R.id.startTxtView)
    TextView startTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        setupViews();
    }


    final int[] drawablearray = new int[]{R.drawable.red_button, R.drawable.red_button, R.drawable.red_button, R.drawable.red_button,
            R.drawable.red_button, R.drawable.green_button, R.drawable.green_button, R.drawable.green_button, R.drawable.bonus_button};

    private void changeBackground(final int time) {
        if (allowed) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Random r = new Random();
                    int tmp = r.nextInt(9);
                    changeBackgroundDrawable(tmp);
                    changeBackground(time);
                }
            }, time);
        }
    }

    private void changeBackgroundDrawable(int position) {
        buttonRel.setBackground(getDrawable(drawablearray[position]));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startRel:
                counter = 0;
                timer.start();
                allowed = true;
                changeBackground(800);
                break;
//            case R.id.lvl2Rel:
//                allowed = true;
//                changeBackground(500);
//                break;
//            case R.id.lvl3Rel:
//                allowed = true;
//                changeBackground(300);
//                break;
            case R.id.buttonRel:
                if (allowed) {
                    Drawable drawable = buttonRel.getBackground();
                    Drawable green = getDrawable(R.drawable.green_button);
                    Drawable red = getDrawable(R.drawable.red_button);
                    Drawable bonus = getDrawable(R.drawable.bonus_button);
                    if (areDrawablesIdentical(drawable, green)) {
                        counter++;
                        scoreTxtView.setText(getResources().getString(R.string.play_activity_score) + " " + counter);
                    } else if (areDrawablesIdentical(drawable, red)) {
                        counter--;
                        scoreTxtView.setText(getResources().getString(R.string.play_activity_score) + " " + counter);
                    } else if (areDrawablesIdentical(drawable, bonus)){
                        counter = counter + 5;
                        scoreTxtView.setText(getResources().getString(R.string.play_activity_score) + " " + counter);
                    }
                }
                break;
        }
    }

    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }

    private void setupViews(){
        levelTxtView.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));
        timeTxtView.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));
        scoreTxtView.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));
        startTxtView.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));

        timeTxtView.setText(getResources().getString(R.string.play_activity_time) + " " + 30 + "\"");
        scoreTxtView.setText(getResources().getString(R.string.play_activity_score) + " " + 0);

        timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeTxtView.setText(getResources().getString(R.string.play_activity_time) + " " + millisUntilFinished / 1000 + "\"");
            }

            public void onFinish() {
                timeTxtView.setText(getResources().getString(R.string.play_activity_time) + " " + 0);
                allowed = false;
            }

        };

        startRel.setOnClickListener(this);
        buttonRel.setOnClickListener(this);
    }
}
