package se233.chapter2.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FetchDataTest {
    @Test
    void normalizeHistoryDaysCapsAtThirty() {
        assertEquals(30, FetchData.normalizeHistoryDays(45));
        assertEquals(30, FetchData.normalizeHistoryDays(30));
        assertEquals(10, FetchData.normalizeHistoryDays(10));
        assertEquals(1, FetchData.normalizeHistoryDays(0));
    }

    @Test
    void validatesCurrencyCodeFormat() {
        assertTrue(FetchData.isValidCurrencyCode("USD"));
        assertTrue(FetchData.isValidCurrencyCode("usd"));
        assertFalse(FetchData.isValidCurrencyCode("US"));
        assertFalse(FetchData.isValidCurrencyCode("USDT"));
        assertFalse(FetchData.isValidCurrencyCode("123"));
        assertFalse(FetchData.isValidCurrencyCode(""));
    }
}
