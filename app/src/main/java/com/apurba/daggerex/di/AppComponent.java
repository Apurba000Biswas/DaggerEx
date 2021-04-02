package com.apurba.daggerex.di;


import android.app.Application;

import com.apurba.daggerex.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{

        // BindsInstance is not mandatory
        @BindsInstance
        Builder application(Application application);

        // calling build() is mandatory
        AppComponent build();
    }
}
