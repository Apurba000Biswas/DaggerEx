package com.apurba.daggerex.ui.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    @Inject
    public AuthViewModel() {
        Log.d("Test", "AuthViewModel is working!!");
    }
}
