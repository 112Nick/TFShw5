package ru.mail.park.myapplication;

import android.app.Application;
import android.content.Context;

import ru.mail.park.myapplication.utils.AppDelegate;
import timber.log.Timber;

public class App extends Application {




    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppDelegate.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}





