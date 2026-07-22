package se233.chapter2.controller;

import se233.chapter2.model.Currency;

public class UnWatchTask {
    public void execute(Currency currency) {
        currency.setWatch(false);
        currency.setWatchRate(0.0);
    }
}
