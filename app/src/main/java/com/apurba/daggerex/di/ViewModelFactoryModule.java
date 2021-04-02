package com.apurba.daggerex.di;

import androidx.lifecycle.ViewModelProvider;

import com.apurba.daggerex.ui.viewModel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
    
}
