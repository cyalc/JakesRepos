package com.cyalc.jakesrepos.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.cyalc.jakesrepos.data.api.GithubApi;
import com.cyalc.jakesrepos.data.dao.RepoDao;
import com.cyalc.jakesrepos.data.db.GithubDb;
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
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class DataModule {

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    private static final String BASE_URL = "https://api.github.com";
    private static final String DATE_FORMAT = "YYYY-MM-DD'T'HH:MM:SSZ";

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static GithubApi provideGithubApi(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

    @Singleton
    @Provides
    static GithubDb provideDb(Application app) {
        return Room
                .databaseBuilder(app, GithubDb.class, "github.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static RepoDao provideRepoDao(GithubDb githubDb) {
        return githubDb.repoDao();
    }
}
