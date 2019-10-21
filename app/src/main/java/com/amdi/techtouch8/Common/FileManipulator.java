package com.amdi.techtouch8.Common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileManipulator {

    public  void copyFile(File fileFrom, File fileTo){

        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(fileFrom));
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileTo));
            byte[] bytes = new byte[1024];
            while (bufferedInputStream.read(bytes) != -1){
                bufferedOutputStream.write(bytes);
            }
            bufferedOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void copyFolder(File fileFrom,File fileTo){
        if(fileFrom.isDirectory()){
            File newFolder = new File(fileTo,fileFrom.getName());
            newFolder.mkdirs();
            File[] fileArray = fileFrom.listFiles();
            for (File file: fileArray) {
                copyFolder(file,newFolder);
            }
        }else {
            File newFile = new File(fileTo,fileFrom.getName());
            copyFile(fileFrom,newFile);
        }
    }



}
