package com.raycoarana.thesearchbattle.model;

public class DataSetDefinition {

    private final String mSource;
    private final String mTerm;

    public DataSetDefinition(String source, String term) {
        mSource = source;
        mTerm = term;
    }

    public String getSource() {
        return mSource;
    }

    public String getTerm() {
        return mTerm;
    }
}
