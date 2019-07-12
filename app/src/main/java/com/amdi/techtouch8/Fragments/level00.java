package com.amdi.techtouch8.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.amdi.techtouch8.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amdi.techtouch8.Common.Common.SELECT_IMAGE;
import static com.amdi.techtouch8.Common.Common.context;
import static com.amdi.techtouch8.Common.Common.currentCubeIsRecording;
import static com.amdi.techtouch8.Common.Common.getPath;
import static com.amdi.techtouch8.Common.Common.imageTapped;
import static com.amdi.techtouch8.Common.Common.isChangeImage;


public class level00 extends Fragment  {

    @BindView(R.id.level00_00) ImageView imageView0;
    @BindView(R.id.level00_01) ImageView imageView1;
    @BindView(R.id.level00_02) ImageView imageView2;
    @BindView(R.id.level00_03) ImageView imageView3;
    @BindView(R.id.level00_04) ImageView imageView4;
    @BindView(R.id.level00_05) ImageView imageView5;
    @BindView(R.id.level00_06) ImageView imageView6;
    @BindView(R.id.level00_07) ImageView imageView7;

    SharedPreferences sharedPreferences;
    String[] image = {"image01","image02","image03","image04","image05","image06","image07","image08"};





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_level00, container, false);
        ButterKnife.bind(this,view);

        sharedPreferences = this.getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        String[] selectedImage = getImagePath(image);
        loadImage(selectedImage);



        imageView0.setOnClickListener(v -> {

            imageTapped(v);
            setBackCubeBackground(v);

        });
        imageView1.setOnClickListener(v -> {
            imageTapped(v);
            setBackCubeBackground(v);

        });
        imageView2.setOnClickListener(v -> {
            imageTapped(v);
            setBackCubeBackground(v);

        });
        imageView3.setOnClickListener(v -> {

            imageTapped(v);
            setBackCubeBackground(v);

        });
        imageView4.setOnClickListener(v -> {
            imageTapped(v);
            setBackCubeBackground(v);

        });
        imageView5.setOnClickListener(v -> {
            imageTapped(v);
            setBackCubeBackground(v);

        });
        imageView6.setOnClickListener(v -> {

            imageTapped(v);
            setBackCubeBackground(v);

        });
        imageView7.setOnClickListener(v -> {
            imageTapped(v);
            setBackCubeBackground(v);

        });

        imageView0.setOnLongClickListener(v -> {

            if(isChangeImage) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[0]);
            }
            return true;
        });
        imageView1.setOnLongClickListener(v -> {

            if(isChangeImage) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[1]);
            }
            return true;
        });
        imageView2.setOnLongClickListener(v -> {
            if(isChangeImage) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[2]);
            }
            return true;
        });
        imageView3.setOnLongClickListener(v -> {
            if(isChangeImage) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[3]);
            }
            return true;
        });
        imageView4.setOnLongClickListener(v -> {
            if(isChangeImage) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[4]);
            }
            return true;
        });
        imageView5.setOnLongClickListener(v -> {
            if(isChangeImage) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[5]);
            }
            return true;
        });
        imageView6.setOnLongClickListener(v -> {
            if(isChangeImage) {

                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[6]);
            }
                return true;

        });
        imageView7.setOnLongClickListener(v -> {
            if(isChangeImage) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra("imageName",imageName);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_IMAGE[7]);
            }
            return true;
        });




        return view;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       switch (requestCode){
           case 1000:
                if(resultCode == Activity.RESULT_OK){
                if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView0.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[0],selectedImage).apply();

                }
            }
                break;
           case 2000:
               if(resultCode == Activity.RESULT_OK){
                   if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView1.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[1],selectedImage).apply();

                   }
               }
               break;
           case 3000:
               if(resultCode == Activity.RESULT_OK){
                   if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView2.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[2],selectedImage).apply();

                   }
               }
               break;
           case 4000:
               if(resultCode == Activity.RESULT_OK){
                   if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView3.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[3],selectedImage).apply();

                   }
               }
               break;
           case 5000:
               if(resultCode == Activity.RESULT_OK){
                   if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView4.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[4],selectedImage).apply();

                   }
               }
               break;
           case 6000:
               if(resultCode == Activity.RESULT_OK){
                   if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView5.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[5],selectedImage).apply();

                   }
               }
               break;
           case 7000:
               if(resultCode == Activity.RESULT_OK){
                   if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView6.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[6],selectedImage).apply();

                   }
               }
               break;
           case 8000:
               if(resultCode == Activity.RESULT_OK){
                   if(data != null){

                       Uri selectedImageUri = data.getData();
                       imageView7.setImageURI(selectedImageUri);
                       String selectedImage = getPath( getActivity( ).getApplicationContext( ), selectedImageUri );
                       sharedPreferences.edit().putString(image[7],selectedImage).apply();

                   }
               }
               break;
       }

    }

    public  void setBackCubeBackground(View view){

            if (currentCubeIsRecording == false) {

                imageView0.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));
                imageView1.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));
                imageView2.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));
                imageView3.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));
                imageView4.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));
                imageView5.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));
                imageView6.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));
                imageView7.setBackground( ContextCompat.getDrawable(context,R.drawable.playing_background));

            }

    }

    public String[] getImagePath(String[] storeKey){
        String[] path = new String[8];
        for(int i = 0; i < storeKey.length; i++){
            String selectedImage = sharedPreferences.getString(storeKey[i],"None");
            path[i] = selectedImage;
        }

        return path;
    }

    public void loadImage(String[] imagePath){
        if(imagePath[0] != "None"){
            File image = new File(imagePath[0]);
            if(image.exists())
            Picasso.get().load(image).into(imageView0);
        }
        if(imagePath[1] != "None"){
            File image = new File(imagePath[1]);
            if(image.exists())
            Picasso.get().load(Uri.parse("file://" + imagePath[1])).into(imageView1);
        }
        if(imagePath[2] != "None"){
            File image = new File(imagePath[2]);
            if(image.exists())
            Picasso.get().load(Uri.parse("file://" + imagePath[2])).into(imageView2);
        }
        if(imagePath[3] != "None"){
            File image = new File(imagePath[3]);
            if(image.exists())
            Picasso.get().load(Uri.parse("file://" + imagePath[3])).into(imageView3);
        }
        if(imagePath[4] != "None"){
            File image = new File(imagePath[4]);
            if(image.exists())
            Picasso.get().load(Uri.parse("file://" + imagePath[4])).into(imageView4);
        }
        if(imagePath[5] != "None"){
            File image = new File(imagePath[5]);
            if(image.exists())
            Picasso.get().load(Uri.parse("file://" + imagePath[5])).into(imageView5);
        }
        if(imagePath[6] != "None"){
            File image = new File(imagePath[6]);
            if(image.exists())
            Picasso.get().load(Uri.parse("file://" + imagePath[6])).into(imageView6);
        }
        if(imagePath[7] != "None"){
            File image = new File(imagePath[7]);
            if(image.exists())
            Picasso.get().load(Uri.parse("file://" + imagePath[7])).into(imageView7);
        }
    }



}