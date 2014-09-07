package com.raycoarana.thesearchbattle.search;

import android.database.Cursor;

import com.raycoarana.thesearchbattle.database.Database;
import com.raycoarana.thesearchbattle.io.ResultsRegister;
import com.raycoarana.thesearchbattle.model.Car;

import java.util.ArrayList;
import java.util.List;

public class MemorySearch extends BaseSearchEngine {

    private final Database mDatabase;
    private List<Car> mCars;

    public MemorySearch(Database database) {
        mDatabase = database;
    }

    @Override
    protected List<Car> onSearch(String term) {
        ArrayList<Car> results = new ArrayList<Car>();
        for(Car car : mCars) {
            this.contained(term.toLowerCase(),
                           car.getCountry(),
                           car.getBrand(),
                           car.getName(),
                           String.valueOf(car.getYear()));
        }

        return results;
    }

    private boolean contained(String term, String... texts) {
        for(String text : texts) {
            if(text != null && text.contains(term)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void prepareSet(String currentSource, ResultsRegister resultsRegister) {
        setResultsRegister(resultsRegister);
        resultsRegister.start(currentSource, MemorySearch.class.getSimpleName());
        Cursor cursor = mDatabase.executeQuery("SELECT * FROM " + currentSource);
        mCars = Car.fromCursor(cursor);
    }
}
