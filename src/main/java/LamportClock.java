package main.java;

public class LamportClock {
    private int timestamp;

    public LamportClock() {
        this.timestamp = 0;
    }

    public void increment() {
        this.timestamp++;
    }

    public void synchronize(int externalTimestamp) {
        this.timestamp = Math.max(this.timestamp, externalTimestamp) + 1;
    }

    public int getCurrentTime() {
        return this.timestamp;
    }
}
