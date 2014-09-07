package com.raycoarana.thesearchbattle.search;

import android.database.Cursor;

import com.raycoarana.thesearchbattle.database.Database;
import com.raycoarana.thesearchbattle.io.ResultsRegister;
import com.raycoarana.thesearchbattle.model.Car;

import java.util.List;

public class FTSSearch extends BaseSearchEngine {

    public static final String FTS_QUERY_TEMPLATE = "SELECT * FROM %s WHERE _id IN (SELECT docid FROM %s_fts WHERE content MATCH ?)";

    private final Database mDatabase;
    private String mQuery;

    public FTSSearch(Database database) {
        mDatabase = database;
    }

    @Override
    protected List<Car> onSearch(String term) {
        Cursor cursor = mDatabase.executeQuery(mQuery, new String[]{ term + "*" });
        return Car.fromCursor(cursor);
    }

    @Override
    public void prepareSet(String currentSource, ResultsRegister resultsRegister) {
        setResultsRegister(resultsRegister);
        resultsRegister.start(currentSource, FTSSearch.class.getSimpleName());
        mQuery = String.format(FTS_QUERY_TEMPLATE, currentSource, currentSource);
    }
}
