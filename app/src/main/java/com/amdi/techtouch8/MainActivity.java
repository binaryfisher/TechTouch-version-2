package com.amdi.techtouch8;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.amdi.techtouch8.Fragments.level00;
import com.amdi.techtouch8.Fragments.level01;
import com.amdi.techtouch8.Fragments.level02;
import com.amdi.techtouch8.Fragments.level03;
import com.amdi.techtouch8.Fragments.level04;
import com.amdi.techtouch8.Fragments.level05;
import com.amdi.techtouch8.Fragments.level06;
import com.amdi.techtouch8.Fragments.level07;
import com.amdi.techtouch8.Fragments.level08;
import com.amdi.techtouch8.Fragments.level09;
import com.amdi.techtouch8.Fragments.level10;
import com.amdi.techtouch8.Fragments.level11;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amdi.techtouch8.Common.Common.context;
import static com.amdi.techtouch8.Common.Common.currentCubeIsRecording;
import static com.amdi.techtouch8.Common.Common.isChangeImage;
import static com.amdi.techtouch8.Common.Common.isRecorderStopedOnPause;
import static com.amdi.techtouch8.Common.Common.isRecording;
import static com.amdi.techtouch8.Common.Common.isRestoring;
import static com.amdi.techtouch8.Common.Common.level;
import static com.amdi.techtouch8.Common.Common.mediaPlayer;
import static com.amdi.techtouch8.Common.Common.mediaRecorder;

import static com.amdi.techtouch8.Common.Common.playSoundEffect;
import static com.amdi.techtouch8.Common.Common.recordButton;
import static com.amdi.techtouch8.Common.Common.sharedPreferences;
import static com.amdi.techtouch8.Common.Common.stopPlay;
import static com.amdi.techtouch8.Common.Common.stopRecord;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSION_CODE = 1000;
    private AlphaAnimation buttonClick;

    @BindView(R.id.restore) ImageView restoreButton;
    @BindView(R.id.change_image) ImageView changeImageButton;
    @BindView(R.id.indicator) TextView indicatorText;
    @BindView(R.id.main_page) ImageView mainPageBtn;

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

        sharedPreferences = this.getSharedPreferences("com.amdi.techtouch8",Context.MODE_PRIVATE);
        level = sharedPreferences.getInt("level",0);
        changeLevel(level);
        upDateIndicatorText();

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
                changeImageButton.setEnabled(false);
                restoreButton.setImageResource(R.drawable.restoring);
                Uri uri = Uri.parse("android.resource://com.amdi.techtouch8/raw/restore_sound_effect");
                playSoundEffect(v,uri);

            }else{
                recordButton.setEnabled(true);
                changeImageButton.setEnabled(true);
                restoreButton.setImageResource(R.drawable.restore);
            }


        });

       changeImageButton.setOnClickListener(v -> {
           isChangeImage = !isChangeImage;

           if(isChangeImage){
               recordButton.setEnabled(false);
               restoreButton.setEnabled(false);
               changeImageButton.setImageResource(R.drawable.changeing_image);
               Uri uri = Uri.parse("android.resource://com.amdi.techtouch8/raw/change_image_sound_effect");
               playSoundEffect(v,uri);

           }else {
               recordButton.setEnabled(true);
               restoreButton.setEnabled(true);
               changeImageButton.setImageResource(R.drawable.change_image);
           }
       });
       mainPageBtn.setOnClickListener(v -> {
           goBackToMainPage();
       });


    }

   @Override
    protected void onPause() {
        super.onPause();
        if(currentCubeIsRecording){
            stopRecord();
            isRecorderStopedOnPause = true;
        }

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
        upDateIndicatorText();

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
        upDateIndicatorText();

    }

    public void recordButtonTapped(View view){

        isRecording = !isRecording;
           if(isRecording) {
               restoreButton.setEnabled(false);
               changeImageButton.setEnabled(false);
               recordButton.setImageResource(R.drawable.recording);
               Uri uri = Uri.parse("android.resource://com.amdi.techtouch8/raw/record_sound_effect");
               playSoundEffect(view,uri);
           }else{
               stopPlay();
               restoreButton.setEnabled(true);
               changeImageButton.setEnabled(true);
               recordButton.setImageResource(R.drawable.record);
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

    public void goBackToMainPage(){
        level = 0;
        changeLevel(0);
        upDateIndicatorText();
    }

    public void upDateIndicatorText(){
        indicatorText.setText("Level " + (level + 1));
    }



}
