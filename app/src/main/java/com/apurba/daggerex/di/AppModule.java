package com.apurba.daggerex.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    static String someString(){
        return "This is Test String";
    }

    @Provides
    static boolean getApp(Application application){
        return application == null;
    }
}
