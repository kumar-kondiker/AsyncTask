package com.kumar.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedimageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedimageview=findViewById(R.id.image_view);


    }


    public class DownloadImage extends AsyncTask<String,Void, Bitmap>
    {

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("heloo");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
          progressDialog.dismiss();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            URL url;
            HttpsURLConnection httpsURLConnection;

            try {
                url=new URL(strings[0]);
                httpsURLConnection= (HttpsURLConnection) url.openConnection();
                httpsURLConnection.connect();

                InputStream inputStream=httpsURLConnection.getInputStream();

                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void downloadImage(View view)
    {
        DownloadImage downloadImage=new DownloadImage();


        try {
            Bitmap bitmap=downloadImage.execute("https://media.nationalgeographic.org/assets/photos/324/759/f452545d-0c94-42c7-a2ff-245c51842087.jpg").get();
            downloadedimageview.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
