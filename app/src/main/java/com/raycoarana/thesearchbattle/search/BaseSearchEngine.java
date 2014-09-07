package com.raycoarana.thesearchbattle.search;

import android.util.Log;

import com.raycoarana.thesearchbattle.io.ResultsRegister;
import com.raycoarana.thesearchbattle.model.Car;
import com.raycoarana.thesearchbattle.model.Timer;

import java.util.List;

public abstract class BaseSearchEngine implements SearchEngine {

    private ResultsRegister mResultsRegister;

    protected void setResultsRegister(ResultsRegister resultsRegister) {
        mResultsRegister = resultsRegister;
    }

    @Override
    public List<Car> search(String term) {
        Timer timer = Timer.start();
        List<Car> results = this.onSearch(term);
        timer.stop();
        Log.i(getClass().getSimpleName(), timer.toString());
        mResultsRegister.addResult(timer.toLong());
        return results;
    }

    protected abstract List<Car> onSearch(String term);

}
