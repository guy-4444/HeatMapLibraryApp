package com.classy.heatmaplibraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.classy.heatmaplibrary.HeatMap;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout main_LAY_root;
    private ImageView main_IMG_heatMap;
    private MaterialButton main_BTN_start;
    private ProgressBar main_PRG_heat;

    private double[][] screenData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_LAY_root = findViewById(R.id.main_LAY_root);
        main_IMG_heatMap = findViewById(R.id.main_IMG_heatMap);
        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_PRG_heat = findViewById(R.id.main_PRG_heat);
        main_PRG_heat.setVisibility(View.GONE);

        main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        calculateTouchScreenResolution(main_LAY_root);

        int width  = main_LAY_root.getMeasuredWidth();
        int height = main_LAY_root.getMeasuredHeight();
    }

    private void calculateTouchScreenResolution(View view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int width  = view.getMeasuredWidth();
                int height = view.getMeasuredHeight();
                Log.d("pttt", "width= " + width + ", height= " + height);
                initScreenListener(view, height, width);
            }
        });
    }

    private void initScreenListener(View view, int height, int width) {
        screenData = new double[height][width];

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("pttt", "Action: " + event.getAction());

                int x = (int) event.getX();
                int y = (int) event.getY();
                //screenData[y][x] += 0.1;


                int R = 10;
                for (int i = -R; i <= R; i++) {
                    for (int j = -R; j <= R; j++) {
                        double val = Math.sqrt(i*i + j*j);
                        val = val == 0 ? 1.0 : 1.0 / val;
                        screenData[y+i][x+j] += val;
                    }
                }

                return false;
            }
        });
    }

    private void start() {
        main_PRG_heat.setVisibility(View.VISIBLE);
        main_BTN_start.setVisibility(View.GONE);

        HeatMap.generateBitmap(screenData, new HeatMap.CallBack_HeatMap() {
            @Override
            public void bitmapReady(Bitmap bitmap) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        main_PRG_heat.setVisibility(View.GONE);
                        main_IMG_heatMap.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}
/*
[21A10242] Guy Isakov 123456789
TomC@mail.afeka.ac.il

Create library module
Commits
Upload to Jitpack
Edit Readme file (GitHub)
 */

