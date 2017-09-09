package com.example.dimitris.touchmedemo;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.onePlayerRel)
    RelativeLayout onePlayerRell;
    @BindView(R.id.twoPlayersRel)
    RelativeLayout twoPlayersRel;
    @BindView(R.id.exitRel)
    RelativeLayout exitRel;
    @BindView(R.id.onePlayerTxtView)
    TextView onePlayerTxtView;
    @BindView(R.id.twoPlayersTxtView)
    TextView twoPlayersTxtView;
    @BindView(R.id.exitTxtView)
    TextView exitTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        onePlayerRell.setOnClickListener(this);
        twoPlayersRel.setOnClickListener(this);
        exitRel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onePlayerRel:
                Intent intent1 = new Intent(this, PlayActivity.class);
                intent1.putExtra("players", 1);
                startActivity(intent1);
                break;
            case R.id.twoPlayersRel:
                Intent intent2 = new Intent(this, PlayActivity.class);
                intent2.putExtra("players", 2);
                startActivity(intent2);
                break;
            case R.id.exitRel:
                finish();
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
}
