package me.lancer.spineruntimesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import me.lancer.spineruntimesdemo.activity.SpineActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAlien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlien = (Button) findViewById(R.id.btn_alien);
        btnAlien.setOnClickListener(vOnClickListener);
    }

    View.OnClickListener vOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == btnAlien) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SpineActivity.class);
                startActivity(intent);
            }
        }
    };
}
