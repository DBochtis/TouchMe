package com.example.dimitris.touchmedemo.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dimitris.touchmedemo.R;
import com.example.dimitris.touchmedemo.helper.TypefacesHelper;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Play2Activity extends AppCompatActivity implements View.OnClickListener{

    private boolean allowed = false;
    private int counter = 0, players;
    private CountDownTimer timer;

    @BindView(R.id.buttonRel1)
    RelativeLayout buttonRel1;
    @BindView(R.id.buttonRel2)
    RelativeLayout buttonRel2;
    @BindView(R.id.startRel)
    RelativeLayout startRel;
    @BindView(R.id.startTxtView)
    TextView startTxtView;
    @BindView(R.id.timeTxtView1)
    TextView timeTxtView1;
    @BindView(R.id.scoreTxtView1)
    TextView scoreTxtView1;
    @BindView(R.id.timeTxtView2)
    TextView timeTxtView2;
    @BindView(R.id.scoreTxtView2)
    TextView scoreTxtView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2);
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
        buttonRel1.setBackground(getDrawable(drawablearray[position]));
        buttonRel2.setBackground(getDrawable(drawablearray[position]));
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
            case R.id.buttonRel1:
                if (allowed) {
                    setButtonDrawable(buttonRel1.getBackground(), scoreTxtView1);
                }
                break;
            case R.id.buttonRel2:
                if (allowed) {
                    setButtonDrawable(buttonRel2.getBackground(), scoreTxtView2);
                }
                break;
        }
    }

    private void setButtonDrawable(Drawable drawable, TextView score){
        Drawable green = getDrawable(R.drawable.green_button);
        Drawable red = getDrawable(R.drawable.red_button);
        Drawable bonus = getDrawable(R.drawable.bonus_button);
        if (areDrawablesIdentical(drawable, green)) {
            counter++;
            score.setText(getResources().getString(R.string.play_activity_score) + " " + counter);
        } else if (areDrawablesIdentical(drawable, red)) {
            counter--;
            score.setText(getResources().getString(R.string.play_activity_score) + " " + counter);
        } else if (areDrawablesIdentical(drawable, bonus)){
            counter = counter + 5;
            score.setText(getResources().getString(R.string.play_activity_score) + " " + counter);
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
        timeTxtView1.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));
        timeTxtView2.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));
        scoreTxtView1.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));
        scoreTxtView2.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));
        startTxtView.setTypeface(TypefacesHelper.get(this, TypefacesHelper.FONT_VR_R));

        timeTxtView1.setText(getResources().getString(R.string.play_activity_time) + " " + 30 + "\"");
        timeTxtView2.setText(getResources().getString(R.string.play_activity_time) + " " + 30 + "\"");
        scoreTxtView1.setText(getResources().getString(R.string.play_activity_score) + " " + 0);
        scoreTxtView2.setText(getResources().getString(R.string.play_activity_score) + " " + 0);

        timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeTxtView1.setText(getResources().getString(R.string.play_activity_time) + " " + millisUntilFinished / 1000 + "\"");
                timeTxtView2.setText(getResources().getString(R.string.play_activity_time) + " " + millisUntilFinished / 1000 + "\"");
            }

            public void onFinish() {
                timeTxtView1.setText(getResources().getString(R.string.play_activity_time) + " " + 0);
                timeTxtView2.setText(getResources().getString(R.string.play_activity_time) + " " + 0);
                allowed = false;
            }

        };

        startRel.setOnClickListener(this);
        buttonRel1.setOnClickListener(this);
        buttonRel2.setOnClickListener(this);
    }
}
