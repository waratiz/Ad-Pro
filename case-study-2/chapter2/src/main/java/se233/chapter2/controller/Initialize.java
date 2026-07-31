package se233.chapter2.controller;

import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

public class Initialize {
    public static List<Currency> initializeApp() {
        Currency c = new Currency("USD");
        List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 30);
        c.setHistorical(cList);
        if (cList != null && !cList.isEmpty()) {
            c.setCurrent(cList.get(cList.size() - 1));
        } else {
            System.err.println("Warning: ไม่สามารถดึงข้อมูลสกุลเงินได้ ");
        }
        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(c);
        return currencyList;
    }
}