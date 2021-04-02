package com.apurba.daggerex.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.apurba.daggerex.R;
import com.apurba.daggerex.ui.viewModel.AuthViewModel;
import com.apurba.daggerex.ui.viewModel.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;
import com.google.android.material.textfield.TextInputEditText;
import com.jaeger.library.StatusBarUtil;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class AuthActivity extends DaggerAppCompatActivity {


    private TextView tvSplash;
    private Button button;
    private TextInputEditText editTextLayout;
    private TextView headerTv;

    @Inject
    RequestManager glide;

    @Inject
    ViewModelProviderFactory providerFactory;

    private AuthViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        tvSplash = findViewById(R.id.tv_splash);
        tvSplash.setVisibility(View.VISIBLE);

        button = findViewById(R.id.button);
        button.setVisibility(View.GONE);

        headerTv = findViewById(R.id.textView2);
        headerTv.setVisibility(View.GONE);

        editTextLayout = findViewById(R.id.textInput);
        editTextLayout.setVisibility(View.GONE);

        int secondsDelayed = 2;
        new Handler().postDelayed(this::setMainView, secondsDelayed * 1000);



        viewModel = new ViewModelProvider(this, providerFactory).get(AuthViewModel.class);

    }

    private void setMainView(){
        tvSplash.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
        editTextLayout.setVisibility(View.VISIBLE);
        headerTv.setVisibility(View.VISIBLE);

        glide.load("https://www.localwebdesignconsultant.com.au/wp-content/uploads/2016/04/Graphic-Design-Course.jpg")
                .into((ImageView) findViewById(R.id.imageView));

        StatusBarUtil.setTransparent(AuthActivity.this);

    }


}