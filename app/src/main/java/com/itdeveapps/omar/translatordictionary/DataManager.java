package com.itdeveapps.omar.translatordictionary;

import android.annotation.SuppressLint;
import android.app.Application;

import com.itdeveapps.omar.translatordictionary.Api.OxfordApiService;
import com.itdeveapps.omar.translatordictionary.Api.OxfordServiceFactory;
import com.itdeveapps.omar.translatordictionary.model.SearchResponse;
import com.itdeveapps.omar.translatordictionary.repo.DataBase;
import com.itdeveapps.omar.translatordictionary.repo.SearchHistoryEntry;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Callback;

public class DataManager {

    private static DataManager instance;
    private OxfordApiService oxfordService;
    private DataBase appDatabase;

    private DataManager(Application application) {
        this.oxfordService = OxfordServiceFactory.createService();
        this.appDatabase = DataBase.getInstance(application);

    }

    public static DataManager getInstance(Application application) {
        if (instance == null) instance = new DataManager(application);
        return instance;
    }

    public void search(String word, Callback<SearchResponse> listener) {
        oxfordService.search("en", word, OxfordServiceFactory.APP_ID, OxfordServiceFactory.APP_KEY)
                .enqueue(listener);
    }

    @SuppressLint("CheckResult")
    public void saveSearchHistoryEntry(final String entry) {
        Observable.fromCallable(() -> {
            SearchHistoryEntry searchHistoryEntry = appDatabase.searchHistoryDao().loadSearchHistoryEntryByEntry(entry);
            if (searchHistoryEntry != null) {
                appDatabase.searchHistoryDao().updateSearchHistoryEntry(new SearchHistoryEntry(entry, new Date()));
            } else {
                appDatabase.searchHistoryDao().insertSearchHistoryEntry(new SearchHistoryEntry(entry, new Date()));
            }
            return true;

        });


    }

}
