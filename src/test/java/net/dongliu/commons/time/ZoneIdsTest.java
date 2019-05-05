package net.dongliu.commons.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZoneIdsTest {

    @Test
    void test() {
        assertNotNull(ZoneIds.CTT());
        ZoneIds.PST();
        ZoneIds.americaChicago();
        ZoneIds.EST();
        ZoneIds.MST();
        ZoneIds.JST();
        ZoneIds.VST();
        ZoneIds.ACT();
        ZoneIds.AET();
    }
}