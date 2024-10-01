package main.java;

public class LamportClock {
    private int timestamp;

    public LamportClock() {
        this.timestamp = 0;
    }

    // Increment the clock on every internal event (like PUT or GET)
    public void increment() {
        timestamp++;
    }

    // Synchronize the clock when receiving a timestamp from another server
    public void synchronize(int otherTimestamp) {
        timestamp = Math.max(timestamp, otherTimestamp) + 1;
    }

    // Return the current Lamport timestamp
    public int getCurrentTime() {
        return timestamp;
    }

    // Override the toString method for easier logging/debugging
    @Override
    public String toString() {
        return "LamportClock [timestamp=" + timestamp + "]";
    }
}
