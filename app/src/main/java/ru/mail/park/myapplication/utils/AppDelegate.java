package ru.mail.park.myapplication.utils;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mail.park.myapplication.BuildConfig;

public class AppDelegate {

    private static AppDelegate instance = null;


    private final Api apiService;
//    public final Context context;

    public static AppDelegate getInstance() {
        return instance;
    }




    private AppDelegate(Context context) {
//        this.context = context;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        apiService = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new AppDelegate(context);
        }
    }

    public Api getApiService() {
        return apiService;
    }
}