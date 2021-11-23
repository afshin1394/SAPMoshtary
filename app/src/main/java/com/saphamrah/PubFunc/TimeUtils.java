package com.saphamrah.PubFunc;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

        long starts;

        public static TimeUtils start() {
            return new TimeUtils();
        }

        private TimeUtils() {
            reset();
        }

        public TimeUtils reset() {
            starts = System.currentTimeMillis();
            return this;
        }

        public long time() {
            long ends = System.currentTimeMillis();
            return ends - starts;
        }



        public long time(TimeUnit unit) {
        return  unit.convert(time(), TimeUnit.MILLISECONDS);
        }

}
