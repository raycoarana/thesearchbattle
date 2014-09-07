package com.raycoarana.thesearchbattle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.raycoarana.thesearchbattle.database.Database;
import com.raycoarana.thesearchbattle.io.ResultsRegister;
import com.raycoarana.thesearchbattle.io.StreamExtensions;
import com.raycoarana.thesearchbattle.model.DataSetDefinition;
import com.raycoarana.thesearchbattle.search.FTSSearch;
import com.raycoarana.thesearchbattle.search.MemorySearch;
import com.raycoarana.thesearchbattle.search.SearchEngine;

import java.io.File;

public class BattleArenaActivity extends Activity {

    private AsyncTask<Void, Void, Void> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Database database = openDatabase();
                ResultsRegister resultsRegister = new ResultsRegister();

                DataSetDefinition[] definitions = buildDataSetDefinitions();
                for (DataSetDefinition dataSetDefinition : definitions) {
                    String currentSource = dataSetDefinition.getSource();
                    String termToSearch = dataSetDefinition.getTerm();

                    Log.i("Battle", String.format("======= %s =======", currentSource));

                    SearchEngine[] searchEngines = buildSearchEngines(database);
                    for (SearchEngine searchEngine : searchEngines) {
                        searchEngine.prepareSet(currentSource, resultsRegister);
                        for (int i = 0; i < 10; i++) {
                            searchEngine.search(termToSearch);
                        }
                        resultsRegister.end();
                    }

                    Log.i("Battle", "=======================");

                    prepareForNextRun();
                }

                resultsRegister.print();
                return null;
            }

        };
        mTask.execute();
    }

    private DataSetDefinition[] buildDataSetDefinitions() {
        return new DataSetDefinition[]{
                new DataSetDefinition("cars_10", "Car"),
                new DataSetDefinition("cars_100", "Car"),
                new DataSetDefinition("cars_250", "Car"),
                new DataSetDefinition("cars_500", "Sport"),
                new DataSetDefinition("cars_750", "Sedan"),
                new DataSetDefinition("cars_1000", "NSX"),
                new DataSetDefinition("cars_1200", "SKYLINE"),
        };
    }

    private SearchEngine[] buildSearchEngines(Database database) {
        return new SearchEngine[]{
                new MemorySearch(database),
                new FTSSearch(database),
        };
    }

    private Database openDatabase() {
        File databaseFile = getDatabasePath("cars.db");
        Database database = new Database(databaseFile, getAssets(), new StreamExtensions());
        database.initilize();
        return database;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mTask.cancel(true);
    }

    private void prepareForNextRun() {
        System.gc();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
