package ru.kpfu.kazanguide.dao;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rober_000 on 12.11.2017.
 */

public class GuideDaoWebImpl extends Application {
    private static KazanGuideAPI kazanGuideAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.2:8080")//Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        kazanGuideAPI = retrofit.create(KazanGuideAPI.class);
    }

    public static KazanGuideAPI getApi() {
        return kazanGuideAPI;
    }

}
