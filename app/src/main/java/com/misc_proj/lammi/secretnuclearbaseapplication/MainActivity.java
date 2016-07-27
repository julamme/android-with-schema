package com.misc_proj.lammi.secretnuclearbaseapplication;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mainLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BackgroundLoader("http://answers.unity3d.com/storage/temp/13202-1.png", "image").execute();

    }

    private class BackgroundLoader extends AsyncTask<String, Void, Drawable> {
        private String imageUrl, imageName;

        public BackgroundLoader(String url, String file_name){

        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Drawable doInBackground(String... strings) {

            try {
                InputStream imgStream = (InputStream) this.fetch(this.imageUrl);
                Drawable d = Drawable.createFromStream(imgStream, this.imageName);
                return d;

            } catch  (MalformedURLException e){
                e.printStackTrace();
                return null;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        private Object fetch(String address) throws MalformedURLException,IOException {
            URL url = new URL(address);
            Object content = url.getContent();
            return content;
        }
        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            mainLinearLayout.setBackground(result);
        }

    }



}
