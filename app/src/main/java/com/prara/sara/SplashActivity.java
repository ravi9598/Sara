package com.prara.sara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    boolean flag = true;
    int i = 0;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(20);

        thread.start();
    }

    Thread thread = new Thread(){
        public void run(){
            while(flag){
                //int n=rand.nextInt(10)+1;
                i+=6;
                progressBar.setProgress(i);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i>=20){
                    flag=false;
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
