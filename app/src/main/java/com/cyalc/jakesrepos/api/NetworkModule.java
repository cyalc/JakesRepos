package com.cyalc.jakesrepos.api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkModule {

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    private static final String BASE_URL = "https://api.github.com";
    private static final String DATE_FORMAT = "YYYY-MM-DDTHH:MM:SSZ";

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();
    }

    @Provides
    @Singleton
    static HttpUrl provideBaseUrl() {
        return HttpUrl.parse(BASE_URL);
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(Application app) {

        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }


    @Provides
    @Singleton
    static Retrofit provideRetrofit(HttpUrl baseUrl,
                                    OkHttpClient okHttpClient,
                                    Gson gson) {

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
