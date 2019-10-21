package com.amdi.techtouch8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static com.amdi.techtouch8.Common.Common.importImageLib;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        importImageLib(this);
        new Handler().postDelayed(() -> {
           startActivity(new Intent(SplashScreen.this,MainActivity.class));
           finish();
        },1000);
    }
}
