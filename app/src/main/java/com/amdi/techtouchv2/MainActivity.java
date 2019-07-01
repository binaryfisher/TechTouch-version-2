package com.amdi.techtouchv2;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.amdi.techtouchv2.Fragments.level00;
import com.amdi.techtouchv2.Fragments.level01;
import com.amdi.techtouchv2.Fragments.level02;
import com.amdi.techtouchv2.Fragments.level03;
import com.amdi.techtouchv2.Fragments.level04;
import com.amdi.techtouchv2.Fragments.level05;
import com.amdi.techtouchv2.Fragments.level06;
import com.amdi.techtouchv2.Fragments.level07;
import com.amdi.techtouchv2.Fragments.level08;
import com.amdi.techtouchv2.Fragments.level09;
import com.amdi.techtouchv2.Fragments.level10;
import com.amdi.techtouchv2.Fragments.level11;
import com.amdi.techtouchv2.Common.Common;

import java.io.File;
import java.io.IOException;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amdi.techtouchv2.Common.Common.context;
import static com.amdi.techtouchv2.Common.Common.currentCubeIsRecording;
import static com.amdi.techtouchv2.Common.Common.isRecorderStarted;
import static com.amdi.techtouchv2.Common.Common.isRecording;
import static com.amdi.techtouchv2.Common.Common.isRestoring;
import static com.amdi.techtouchv2.Common.Common.level;
import static com.amdi.techtouchv2.Common.Common.mediaPlayer;
import static com.amdi.techtouchv2.Common.Common.mediaRecorder;

import static com.amdi.techtouchv2.Common.Common.play;
import static com.amdi.techtouchv2.Common.Common.playSoundEffect;
import static com.amdi.techtouchv2.Common.Common.record;
import static com.amdi.techtouchv2.Common.Common.recordButton;
import static com.amdi.techtouchv2.Common.Common.sharedPreferences;
import static com.amdi.techtouchv2.Common.Common.stopPlay;
import static com.amdi.techtouchv2.Common.Common.stopRecord;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_PERMISSION_CODE = 1000;
    private AlphaAnimation buttonClick;





    @BindView(R.id.restore) ImageView restoreButton;
   // @BindView(R.id.record) ImageView recordButton;


     Fragment fragment0 = new level00();
     Fragment fragment1 = new level01();
     Fragment fragment2 = new level02();
     Fragment fragment3 = new level03();
     Fragment fragment4 = new level04();
     Fragment fragment5 = new level05();
     Fragment fragment6 = new level06();
     Fragment fragment7 = new level07();
     Fragment fragment8 = new level08();
     Fragment fragment9 = new level09();
     Fragment fragment10 = new level10();
     Fragment fragment11 = new level11();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        ButterKnife.bind(this);
        if(!checkPermission()){
            requestPermission();
        }

        mediaRecorder = new MediaRecorder();
        mediaPlayer = new MediaPlayer();
        recordButton = findViewById(R.id.record);

        sharedPreferences = this.getSharedPreferences("com.amdi.techtouchv2",Context.MODE_PRIVATE);
        level = sharedPreferences.getInt("level",0);
        changeLevel(level);

        buttonClick = new AlphaAnimation(1F,0.0F);
        mediaRecorder.setOnInfoListener((mr, what, extra) -> {
            if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){

                currentCubeIsRecording = false;
                stopRecord();

            }
        });


       restoreButton.setOnClickListener(v -> {

            isRestoring = !isRestoring;

            if (isRestoring ){
                recordButton.setEnabled(false);
                restoreButton.setImageResource(R.drawable.restoring);
                Uri uri = Uri.parse("android.resource://com.amdi.techtouchv2/raw/restore_sound_effect");
               playSoundEffect(v,uri);

            }else{
                recordButton.setEnabled(true);
                restoreButton.setImageResource(R.drawable.restore);
            }


        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        //if record doesn't stop
        if(currentCubeIsRecording)
            stopRecord();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRecord();
    }

    public void levelUp(View view){


        if(level == 11){
            level = 0;
        }else {
            level++;
        }
        sharedPreferences.edit().putInt("level",level).apply();
        changeLevel(level);
        view.startAnimation(buttonClick);

    }

    public void levelDown(View view){
        if(level == 0){
            level = 11;
        }else {
            level--;
        }
        sharedPreferences.edit().putInt("level",level).apply();
        changeLevel(level);
        view.startAnimation(buttonClick);

    }

    public void recordButtonTapped(View view){

        isRecording = !isRecording;
           if(isRecording) {
               restoreButton.setEnabled(false);
               recordButton.setImageResource(R.drawable.recording);
               Uri uri = Uri.parse("android.resource://com.amdi.techtouchv2/raw/record_sound_effect");
               playSoundEffect(view,uri);
           }else{
               stopPlay();
               restoreButton.setEnabled(true);
               recordButton.setImageResource(R.drawable.record);
           }

    }

    public void restoreButtonTapped(View view) {
        view.startAnimation(buttonClick);

        Uri uri = Uri.parse("android.resource://com.amdi.techtouchv2/raw/restore_sound_effect" );
        try {
            mediaPlayer.setDataSource(getApplicationContext(),uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkPermission() {
        int write_external_storage_result = ContextCompat.
                checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read_external_storage_result = ContextCompat.
                checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.
                checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED
                && read_external_storage_result == PackageManager.PERMISSION_GRANTED
                && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO},
               REQUEST_PERMISSION_CODE);

    }

    public  void changeLevel(int level){
        switch (level){
            case 0:
               switchFragment(fragment0);
                break;
            case 1:
                switchFragment(fragment1);
                break;
            case 2:
                switchFragment(fragment2);
                break;
            case 3:
                switchFragment(fragment3);
                break;
            case 4:
                switchFragment(fragment4);
                break;
            case 5:
                switchFragment(fragment5);
                break;
            case 6:
                switchFragment(fragment6);
                break;
            case 7:
                switchFragment(fragment7);
                break;
            case 8:
                switchFragment(fragment8);
                break;
            case 9:
                switchFragment(fragment9);
                break;
            case 10:
                switchFragment(fragment10);
                break;
            case 11:
                switchFragment(fragment11);
                break;
        }

    }

    public  void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.imageContainer,fragment);
        fragmentTransaction.commit();

    }



}
