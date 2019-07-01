package com.amdi.techtouchv2.Common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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
import com.amdi.techtouchv2.MainActivity;
import com.amdi.techtouchv2.R;

import java.io.File;
import java.io.IOException;

import static com.amdi.techtouchv2.R.color.colorCubePlaying;
import static com.amdi.techtouchv2.R.color.colorCubeRecording;

public class Common {
        public static int level = 0;
       public static SharedPreferences sharedPreferences;
       public static final int[] SELECT_IMAGE = {1000,2000,3000,4000,5000,6000,7000,8000};
       public static boolean isRecorderStarted = false;
       public static Context context;
       public static final int MAX_RECORD_TIME = 10000;//max record time is 10s
       public static boolean isRecording = false; //record function enable or not
       public static boolean isRestoring = false;
       public static boolean currentCubeIsRecording = false;//current cube is recording or notpublic static String recordingCubeTag;public static String nextTapeCubeTag;
       public static MediaRecorder mediaRecorder;
       public static MediaPlayer mediaPlayer;
       public static ImageView recordButton;



    public static  void record(View view) {

        currentCubeIsRecording = !currentCubeIsRecording;
        boolean isRecordBefore = isRecordedBefore(view);

        if(currentCubeIsRecording == true) {

             recordButton.setEnabled(false);
            if(isRecordBefore){
                deleteRecordedFile(view);
            }

            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.recording_background);
            view.setBackground(drawable);
            startRecord(view);


        }else {
                Drawable drawable = ContextCompat.getDrawable(context,R.drawable.playing_background);
                view.setBackground(drawable);
                stopRecord();
                recordButton.setEnabled(true);
        }

    }

    public  static boolean  isRecordedBefore(View view){

        String viewId = String.valueOf(view.getId());
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path,viewId + ".3gpp");
        return file.exists();

    }

    public static void stopRecord() {
        mediaRecorder.stop();
        mediaRecorder.reset();//reuse the MediaRecorder object

    }

    public static void stopPlay(){
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    public static void startRecord(View view) {
        try {
            String viewId = String.valueOf(view.getId());
            String file = Environment.getExternalStorageDirectory() + "/" + viewId + ".3gpp";

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
           // mediaRecorder.setMaxDuration(MAX_RECORD_TIME);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(file);
            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void play(View view){

        boolean isRecordBefore = isRecordedBefore(view);

        stopPlay();

        if( isRecordBefore == false) {

            int id = view.getId();
            String nameId = view.getResources().getResourceEntryName(id);
            Uri uri = Uri.parse("android.resource://com.amdi.techtouchv2/raw/" + nameId);
            //int resourceId = view.getResources().getIdentifier(nameId, "raw", "com.amdi.techtouchv2");

            try {
                mediaPlayer.setDataSource(view.getContext(),uri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

            try {

                String viewId = String.valueOf(view.getId());
                File path = Environment.getExternalStorageDirectory();
                mediaPlayer.setDataSource(path + "/" + viewId + ".3gpp");
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void imageTapped(View view){

        if (isRecording){

            record(view);
        }else if(isRestoring){

            restore(view);
        }else  {

            play(view);
        }

    }

    public static void restore(View view){

        boolean isRecordBefore = isRecordedBefore(view);

        if(isRecordBefore){

            Uri uri = Uri.parse("android.resource://com.amdi.techtouchv2/raw/restore_complete_sound_effect");
            deleteRecordedFile(view);
            playSoundEffect(view, uri);
        }

    }

   public static void deleteRecordedFile(View view) {
        String viewId = String.valueOf(view.getId());
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, viewId + ".3gpp");
        file.delete();
    }

    public static void playSoundEffect(View view, Uri uri) {
        try {
            stopPlay();
            mediaPlayer.setDataSource(view.getContext(), uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get file path for selected path
    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }


}
