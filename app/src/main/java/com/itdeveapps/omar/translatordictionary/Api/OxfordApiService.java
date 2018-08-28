package com.itdeveapps.omar.translatordictionary.Api;

import com.itdeveapps.omar.translatordictionary.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;



public interface OxfordApiService {

    @GET("entries/{source_lang}/{word_id}")
    Call<SearchResponse> search(@Path("source_lang") String sourceLanguage,
                                @Path("word_id") String word,
                                @Header("app_id") String appId,
                                @Header("app_key") String appKey);

}
