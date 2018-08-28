package com.itdeveapps.omar.translatordictionary.Api;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class OxfordServiceFactory {

    private static final String BASE_URL = "https://od-api.oxforddictionaries.com/api/v1/";
    public static final String APP_ID= "app_id";
    public static final String APP_KEY= "app_key";
    private static final int HTTP_READ_TIMEOUT = 10;
    private static final int HTTP_CONNECT_TIMEOUT = 6;

    public static OxfordApiService createService() {
        RxJava2CallAdapterFactory rxAdapter =
                RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .addCallAdapterFactory(rxAdapter)
                .build();

        return retrofit.create(OxfordApiService.class);
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(createLoggingInterceptor());
        return httpClientBuilder.build();
    }

    private static HttpLoggingInterceptor createLoggingInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }


}
