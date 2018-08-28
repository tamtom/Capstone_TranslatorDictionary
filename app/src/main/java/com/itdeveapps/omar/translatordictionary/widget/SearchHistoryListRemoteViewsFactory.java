package com.itdeveapps.omar.translatordictionary.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.itdeveapps.omar.translatordictionary.R;
import com.itdeveapps.omar.translatordictionary.repo.DataBase;
import com.itdeveapps.omar.translatordictionary.repo.SearchHistoryEntry;
import com.itdeveapps.omar.translatordictionary.utils.AppExecutors;

import java.util.List;

import static com.itdeveapps.omar.translatordictionary.widget.SearchHistoryWidgetProvider.EXTRA_SEARCH_ENTRY;

public class SearchHistoryListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private DataBase appDatabase;
    private List<SearchHistoryEntry> searchHistoryEntries;

    public SearchHistoryListRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.appDatabase = DataBase.getInstance(context);
    }

    @Override
    public void onCreate() {
        AppExecutors.getInstance().diskIO().execute(() ->
                searchHistoryEntries = appDatabase.searchHistoryDao().getSearchHistoryEntries());

    }

    @Override
    public void onDataSetChanged() {
        AppExecutors.getInstance().diskIO().execute(() ->
                searchHistoryEntries = appDatabase.searchHistoryDao().getSearchHistoryEntries());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (searchHistoryEntries == null)
            return 0;
        else return searchHistoryEntries.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.item_history_widget);
        SearchHistoryEntry searchHistoryEntry = searchHistoryEntries.get(position);
        remoteView.setTextViewText(R.id.historyTv, searchHistoryEntry.getEntry());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(EXTRA_SEARCH_ENTRY, searchHistoryEntry.getEntry());
        remoteView.setOnClickFillInIntent(R.id.widgetRow, fillInIntent);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
