package se233.chapter2.controller;

import se233.chapter2.Launcher;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.util.Optional;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {
    public static void onRefresh() {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();

            for (Currency c : currencyList) {
                List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 30);

                if (cList != null && !cList.isEmpty()) {
                    c.setHistorical(cList);
                    c.setCurrent(cList.get(cList.size() - 1));
                }
            }

            Launcher.refreshPane();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void onAdd() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);

            Optional<String> code = dialog.showAndWait();

            if (code.isPresent()) {
                String rawCode = code.get().trim().toUpperCase();

                if (!FetchData.isValidCurrencyCode(rawCode)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Currency");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid 3-letter currency code, for example USD.");
                    alert.showAndWait();
                    return;
                }

                if (rawCode.equals(Launcher.getBaseCurrency())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Currency");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot add the same currency as the base currency (" + rawCode + ").");
                    alert.showAndWait();
                    return;
                }

                List<Currency> currencyList = Launcher.getCurrencyList();
                Currency c = new Currency(rawCode);
                List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 30);

                if (!cList.isEmpty() ) {
                    c.setHistorical(cList);
                    c.setCurrent(cList.get(cList.size() - 1));

                    currencyList.add(c);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No Data");
                    alert.setHeaderText(null);
                    alert.setContentText("No exchange rate data was returned for that currency. Please try another code.");
                    alert.showAndWait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void onDelete(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;

            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                currencyList.remove(index);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void onUnwatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;

            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                Currency selectedCurrency = currencyList.get(index);
                selectedCurrency.setWatch(false);
                selectedCurrency.setWatchRate(0.0);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onWatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;

            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Watch");
                dialog.setContentText("Rate:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);

                Optional<String> retrievedRate = dialog.showAndWait();

                if (retrievedRate.isPresent()) {
                    double rate = Double.parseDouble(retrievedRate.get());
                    Currency selectedCurrency = currencyList.get(index);
                    selectedCurrency.setWatch(true);
                    selectedCurrency.setWatchRate(rate);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }

                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}