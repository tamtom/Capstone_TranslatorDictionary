package com.itdeveapps.omar.translatordictionary.home;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import com.itdeveapps.omar.translatordictionary.DataManager;

public class HomeViewModelFactory implements ViewModelProvider.Factory {

    DataManager dataManager;

    Application application;

    public HomeViewModelFactory(Application application, DataManager dataManager) {
        this.application = application;
        this.dataManager = dataManager;
    }

    @Override
    public HomeViewModel create(Class modelClass) {
        return new HomeViewModel(application, dataManager);
    }
}
