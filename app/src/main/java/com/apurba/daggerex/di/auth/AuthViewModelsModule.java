package com.apurba.daggerex.di.auth;

import androidx.lifecycle.ViewModel;

import com.apurba.daggerex.di.ViewModelKey;
import com.apurba.daggerex.ui.viewModel.AuthViewModel;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {


    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
