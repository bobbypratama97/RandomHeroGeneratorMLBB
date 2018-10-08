package com.alfianpp.randomherogeneratormlbb;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 2000;

    private ArrayList<String> all_heroes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        initializeAllHeroes();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("all_heroes", all_heroes);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void initializeAllHeroes() {
        String line;

        InputStream is = getResources().openRawResource(R.raw.heroes_mlbb);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        try {
            while((line = reader.readLine()) != null){
                all_heroes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        all_heroes.remove(0);
    }
}
