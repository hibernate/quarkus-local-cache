package org.infinispan.quarkus.hibernate.cache;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

enum Time {
    ;

    private static final Duration FOREVER = ChronoUnit.FOREVER.getDuration();

    static Duration forever() {
        return FOREVER;
    }

    static boolean isForever(Duration duration) {
        return FOREVER.equals(duration);
    }

    @FunctionalInterface
    interface NanosService {
        long nanoTime();
    }

    @FunctionalInterface
    interface MillisService {
        MillisService SYSTEM = new MillisService() {
            @Override
            public long milliTime() {
                return System.currentTimeMillis();
            }
        };

        long milliTime();
    }

}
