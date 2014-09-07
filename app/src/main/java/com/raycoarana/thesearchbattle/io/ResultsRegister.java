package com.raycoarana.thesearchbattle.io;

import android.util.Log;

public class ResultsRegister {

    StringBuilder mStringBuilder = new StringBuilder();

    public void start(String dataset, String engine) {
        mStringBuilder.append(dataset);
        separator();
        mStringBuilder.append(engine);
        separator();
    }

    public void addResult(long time) {
        mStringBuilder.append(time);
        separator();
    }

    public void end() {
        mStringBuilder.append("\n");
    }

    private void separator() {
        mStringBuilder.append(";");
    }

    public void print() {
        Log.i(ResultsRegister.class.getSimpleName(), mStringBuilder.toString());
    }

}
