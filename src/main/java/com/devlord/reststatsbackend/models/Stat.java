package com.devlord.reststatsbackend.models;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Mohammad
 */
public class Stat {

    private static final int ALLOWED_SEC_DIFF = 60;
    private static Stat _instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock(true);

    public static Stat getInstance() {
        if (null == _instance) {
            _instance = new Stat();
        }
        return _instance;
    }

    double sum;
    double avg;
    double min;
    double max;
    double count;

    private Stat() {
        sum = 0;
        avg = 0;
        min = 0;
        max = 0;
        count = 0;
    }

    public double getSum() {
        return sum;
    }

    public double getAvg() {
        return avg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getCount() {
        return count;
    }

    @Override
    protected Object clone() {
        Stat nInstance = new Stat();
        nInstance.avg = this.avg;
        nInstance.max = this.max;
        nInstance.min = this.min;
        nInstance.sum = this.sum;
        nInstance.count = this.count;
        return nInstance;
    }

    public boolean updateStats(Transaction tran) {

        if (tran.getUTCTime().isBefore(
                ZonedDateTime.now()
                        .toInstant()
                        .atZone(ZoneId.of("UTC"))
                        .minusSeconds(ALLOWED_SEC_DIFF))
                || tran.getUTCTime().isAfter(ZonedDateTime.now().toInstant()
                        .atZone(ZoneId.of("UTC")))) {
            return false;
        }

        try {
            LOCK.lock();
            
            Stat cloned = (Stat) getInstance().clone();
            if (cloned.count == 0 || cloned.min > tran.getAmount()) {
                cloned.min = tran.getAmount();
            }
            if (cloned.count == 0 || cloned.max < tran.getAmount()) {
                cloned.max = tran.getAmount();
            }
            cloned.count++;
            cloned.sum += tran.getAmount();
            cloned.avg = cloned.sum / cloned.count;
            //replace the instance;
            Stat._instance = cloned;
        } finally {
            LOCK.unlock();
        }

        return true;
    }

    protected void reset() {
        sum = 0;
        avg = 0;
        min = 0;
        max = 0;
        count = 0;
    }
}
