package com.itdeveapps.omar.translatordictionary.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class SearchHistoryListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new SearchHistoryListRemoteViewsFactory(getApplicationContext(), intent));
    }
}
