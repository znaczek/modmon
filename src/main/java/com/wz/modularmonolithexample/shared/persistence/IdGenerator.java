package com.wz.modularmonolithexample.shared.persistence;

import java.time.Instant;

public class IdGenerator {


    private static Long lastTimestamp = null;

    private static long sequence = 0L;

    private static final long nodeId;

    static {
        nodeId = (long) (Math.random() * 10);
    }

    public static synchronized String generateId() {
        long currentTimestamp =  Instant.now().getEpochSecond();


        if (lastTimestamp != null && currentTimestamp == lastTimestamp) {
            sequence = sequence + 1;
        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return String.format("%s-%s-%s", currentTimestamp, nodeId, sequence);
    }

}
