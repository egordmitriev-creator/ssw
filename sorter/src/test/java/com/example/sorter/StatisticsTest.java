package com.example.sorter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {

    @Test
    void updateIntegerStats() {
        Statistics stats = new Statistics();

        stats.updateIntegerStats(25);
        stats.updateIntegerStats(52);
        stats.updateIntegerStats(17);

        assertEquals(3, stats.getIntCount());
        assertEquals(17, stats.getMinInt());
        assertEquals(52, stats.getMaxInt());
        assertEquals(94, stats.getIntSum());
    }

    @Test
    void updateFloatStats() {
        Statistics stats = new Statistics();

        stats.updateFloatStats(12.3);
        stats.updateFloatStats(0.5);
        stats.updateFloatStats(28.8);

        assertEquals(3, stats.getFloatCount());
        assertEquals(0.5, stats.getMinFloat());
        assertEquals(28.8, stats.getMaxFloat());
        assertEquals(41.6, stats.getFloatSum());
    }

    @Test
    void updateStringStats() {
        Statistics stats = new Statistics();

        stats.updateStringStats("f1rst");
        stats.updateStringStats("2econdddd");
        stats.updateStringStats("3rd");

        assertEquals(3, stats.getStringCount());
        assertEquals(3, stats.getMinStringLength());
        assertEquals(9, stats.getMaxStringLength());
    }
}