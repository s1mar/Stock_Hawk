package com.sam_chordas.android.stockhawk.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.WidgetService;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

public class StockWidget extends AppWidgetProvider {

    static void update(Context context, AppWidgetManager widgetManager,
                       int appWidgetId) {

        String widgetText = context.getString(R.string.widget_title);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
        rv.setTextViewText(R.id.widget_title, widgetText);
        rv.setRemoteAdapter(R.id.widget_list_view,
                new Intent(context, WidgetService.class));

        Intent iIntent = new Intent(context, MyStocksActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, iIntent, 0);

        rv.setOnClickPendingIntent(R.id.widget_title, appPendingIntent);
        widgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            update(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }
}

