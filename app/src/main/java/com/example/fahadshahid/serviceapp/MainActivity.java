package com.example.fahadshahid.serviceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    Button start;
    Button stop;
    TextView progress;
    EditText limit;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event){
        String str = event.getValue();
        progress.setText(str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.btnStartService);
        stop = findViewById(R.id.btnStopService);
        progress = findViewById(R.id.tvServiceProgress);
        limit = findViewById(R.id.etLimit);

        final Intent intent = new Intent(MainActivity.this, MyIntentService.class);

        EventBus.getDefault().register(this);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb = limit.getText().toString();
                try {
                        int number = Integer.parseInt(numb);
                        intent.putExtra("limit", number);
                        Toast.makeText(MainActivity.this, "Service Started", Toast.LENGTH_SHORT).show();
                        startService(intent);


                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Wrong Limit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
            }
        });
    }
}
