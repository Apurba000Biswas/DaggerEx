package com.apurba.daggerex;

import androidx.annotation.RequiresApi;

import dagger.android.support.DaggerAppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.inject.Inject;


public class AuthActivity extends DaggerAppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 20001;
    private static final int REQ_CODE_PHOTO_GALLERY = 2001;

    private ImageView imageView;

    @Inject
    String whatEverTestVariableForDi;

    @Inject
    boolean isAppNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        imageView = findViewById(R.id.image_view);

        Log.d("Test", whatEverTestVariableForDi);
        Log.d("Test", "OnCreate : is App Null ? " + isAppNull);
    }

    public void onImageCamera(View view) {

        // Link : https://github.com/esafirm/android-image-picker
        //https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library
        ImagePicker.cameraOnly().start(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            //List<Image> images = ImagePicker.getImages(data)
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            Toast.makeText(this, "I am here", Toast.LENGTH_SHORT).show();

            Picasso.get()
                    .load(image.getUri())
                    .fit()
                    .rotate(getRotation(image.getUri()))
                    .centerCrop().into(imageView);
        }


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            getEncodedImage(imageBitmap);
        }else if (requestCode == REQ_CODE_PHOTO_GALLERY && resultCode == RESULT_OK) {

            Uri targetUri = data.getData();


            Bitmap getBitmap = null;
            try {
                InputStream image_stream;
                try {
                    image_stream = this.getContentResolver().openInputStream(targetUri);
                    getBitmap = BitmapFactory.decodeStream(image_stream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*
            Picasso.get()
                    .load(targetUri)
                    .fit()
                    .rotate(getRotation(targetUri))
                    .centerCrop().into(imageView);

             */

            imageView.setImageBitmap(getBitmap);

            //******* Useful resource



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

    public String getEncodedImage(Bitmap bitmap){
        //ImageView profileImageView = rootView.findViewById(R.id.profile_image);
        //Bitmap bitmap = ((BitmapDrawable)profileImageView.getDrawable()).getBitmap();

        if (bitmap == null) return null;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

        byte[] imageBytes = outputStream.toByteArray();
        return  Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}