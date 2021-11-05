package me.lancer.spineruntimesdemo.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import me.lancer.spineruntimesdemo.R;
import me.lancer.spineruntimesdemo.model.SpineTest;

public class SpineActivity extends AndroidApplication {

    SpineTest alien;
    View alienView;
    ViewGroup content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        content = findViewById(R.id.content);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.r = cfg.g = cfg.b = cfg.a = 8;
        cfg.useTextureView = true;
        alien = new SpineTest();
        alienView = initializeForView(alien, cfg);
        if (alienView instanceof SurfaceView) {
            SurfaceView glView = (SurfaceView) alienView;
            glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            glView.setZOrderOnTop(true);
        }
        addAlien();
    }

    public void addAlien() {
//        final WindowManager windowManager = getWindowManager();
//        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        alienView.setOnTouchListener(new View.OnTouchListener() {

            float lastX, lastY;

            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                float x = event.getRawX();
                float y = event.getRawY();
                if (action == MotionEvent.ACTION_DOWN) {
                    lastX = x;
                    lastY = y;
                } else if (action == MotionEvent.ACTION_MOVE) {
                    layoutParams.leftMargin += (int) (x - lastX);
                    layoutParams.topMargin += (int) (y - lastY);
                    content.updateViewLayout(alienView, layoutParams);
                    lastX = x;
                    lastY = y;
                } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    alien.setAnimate();
                }
                return true;
            }
        });
//        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
//        layoutParams.flags = 40;
        layoutParams.width = dp2Px(120) * 2;
        layoutParams.height = dp2Px(150) * 2;
//        layoutParams.format = -3;
        content.addView(alienView, 0, layoutParams);
    }

    public int dp2Px(float value) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    @Override
    protected void onDestroy() {
//        getWindowManager().removeViewImmediate(alienView);
        super.onDestroy();
    }
}
