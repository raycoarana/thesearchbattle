package com.raycoarana.thesearchbattle.model;

public class Timer {

    private final long mStartTimestamp;
    private long mTimesplased;

    public static Timer start() {
        return new Timer();
    }

    private Timer() {
        mStartTimestamp = System.currentTimeMillis();
    }

    public void stop() {
        mTimesplased = System.currentTimeMillis() - mStartTimestamp;
    }

    public String toString() {
        return String.format("Total time: %d ms (%.3f sg)", mTimesplased, mTimesplased / 1000.f);
    }

    public long toLong() {
        return mTimesplased;
    }
}
