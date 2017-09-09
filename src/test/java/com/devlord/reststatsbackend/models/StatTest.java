package com.devlord.reststatsbackend.models;

import java.time.Instant;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mohammad
 */
public class StatTest {

    public StatTest() {
    }

    @Test
    public void testApplyStatsScenario1() {
        long currentTimestamp = Instant.now().toEpochMilli();
        Transaction t = new Transaction().amount(1.0).timestamp(currentTimestamp);
        boolean result = Stat.getInstance().updateStats(t);
        assertTrue("Transaction should be updated", result);
        result = Stat.getInstance().updateStats(t.amount(-1.0));
        assertTrue("Transaction should be updated", result);

        assertEquals("Sum should be 0.0", 0.0, Stat.getInstance().getSum(), 0.0);
        assertEquals("Avg should be 0.0", 0.0, Stat.getInstance().getAvg(), 0.0);
        assertEquals("Min should be -1.0", -1.0, Stat.getInstance().getMin(), 0.0);
        assertEquals("Max should be 1.0", 1.0, Stat.getInstance().getMax(), 0.0);
        assertEquals("Count should be 2", 2, Stat.getInstance().getCount(), 0.0);
    }
    
    @Test
    public void testApplyStatsScenario2() {
        Stat.getInstance().reset();
        long currentTimestamp = Instant.now().toEpochMilli();
        Transaction t = new Transaction().amount(20.0).timestamp(currentTimestamp);
        boolean result = Stat.getInstance().updateStats(t);
        assertTrue("Transaction should be updated", result);
        result = Stat.getInstance().updateStats(t.amount(-10.0));
        assertTrue("Transaction should be updated", result);
        result = Stat.getInstance().updateStats(t.amount(-20.0));
        assertTrue("Transaction should be updated", result);
        
        assertEquals("Sum should be -10.0", -10.0, Stat.getInstance().getSum(), 0.0);
        assertEquals("Min should be -20.0", -20.0, Stat.getInstance().getMin(), 0.0);
        assertEquals("Max should be 20.0", 20.0, Stat.getInstance().getMax(), 0.0);
        assertEquals("Count should be 3", 3, Stat.getInstance().getCount(), 0.0);
        assertEquals("Avg should be -3.3333...", -3.333333333333333, Stat.getInstance().getAvg(), 0.00003);
        
    }
    
    @Test
    public void testApplyStatsScenario3() {
        Stat.getInstance().reset();
        long currentTimestamp = Instant.now().minusSeconds(61).toEpochMilli();
        Transaction t = new Transaction().amount(20.0).timestamp(currentTimestamp);
        boolean result = Stat.getInstance().updateStats(t);
        assertFalse("Transaction should not be updated", result);
        result = Stat.getInstance().updateStats(t.amount(-10.0));
        assertFalse("Transaction should not be updated", result);
    }

}
