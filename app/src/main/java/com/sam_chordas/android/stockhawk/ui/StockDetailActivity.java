package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import com.sam_chordas.android.stockhawk.rest.Utils;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

public class StockDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private LineChartView lineChart;
    private String curr;
    private static final int LOADER_ID = 1;
    public static final String STOCK_NAME = "";

    public static Intent getStartActivityIntent(Context context, String curr) {
        Intent intent = new Intent(context, StockDetailActivity.class);
        intent.putExtra(StockDetailActivity.STOCK_NAME, curr);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        curr = getIntent().getStringExtra(STOCK_NAME);

        setTitle(curr);
        lineChart = (LineChartView) findViewById(R.id.lineChart);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(this, QuoteProvider.Quotes.CONTENT_URI,
                        new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                                QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                        QuoteColumns.SYMBOL + " = ?",
                        new String[]{curr},
                        null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0)           /*if cursor isnt empty then draw chart */
            Utils.ChartEngine(data, lineChart, this);
    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
