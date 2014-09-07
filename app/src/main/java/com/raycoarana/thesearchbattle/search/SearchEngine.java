package com.raycoarana.thesearchbattle.search;

import com.raycoarana.thesearchbattle.io.ResultsRegister;
import com.raycoarana.thesearchbattle.model.Car;

import java.util.List;

public interface SearchEngine {

    void prepareSet(String currentSource, ResultsRegister resultsRegister);
    List<Car> search(String term);

}
