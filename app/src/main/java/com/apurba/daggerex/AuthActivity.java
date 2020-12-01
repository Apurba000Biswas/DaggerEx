package com.apurba.daggerex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class AuthActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 20001;
    private static final int REQ_CODE_PHOTO_GALLERY = 2001;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        imageView = findViewById(R.id.image_view);
    }

    public void onImageCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }else if (requestCode == REQ_CODE_PHOTO_GALLERY && resultCode == RESULT_OK) {

            Uri targetUri = data.getData();
            Picasso.get()
                    .load(targetUri)
                    .fit()
                    .rotate(getRotation(targetUri))
                    .centerCrop().into(imageView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getRotation(Uri targetUri ){
        int angle = 0;
        try {
            InputStream inputStream = Objects.requireNonNull(this)
                    .getContentResolver().openInputStream(targetUri);

            ExifInterface ei = new ExifInterface(inputStream);

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    angle = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return angle;
    }


    public void onImageGallery(View view) {
        Intent picIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picIntent, REQ_CODE_PHOTO_GALLERY);
    }
}