package com.apurba.daggerex.di;

import com.apurba.daggerex.di.auth.AuthViewModelsModule;
import com.apurba.daggerex.ui.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {



    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();

}
