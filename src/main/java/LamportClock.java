package main.java;

public class LamportClock {
    private int timestamp;

    public LamportClock() {
        this.timestamp = 0;
    }

    public void increment() {
        timestamp++;
    }

    public void synchronize(int otherTimestamp) {
        timestamp = Math.max(timestamp, otherTimestamp) + 1;
    }

    public int getCurrentTime() {
        return timestamp;
    }
}
