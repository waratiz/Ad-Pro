package se233.chapter2.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import se233.chapter2.Launcher;
import se233.chapter2.model.CurrencyEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class FetchData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd");
    private static final int MAX_HISTORY_DAYS = 30;

    public static int normalizeHistoryDays(int requestedDays) {
        if (requestedDays <= 0) {
            return 1;
        }
        return Math.min(requestedDays, MAX_HISTORY_DAYS);
    }

    public static boolean isValidCurrencyCode(String symbol) {
        if (symbol == null) {
            return false;
        }

        String normalized = symbol.trim().toUpperCase(Locale.ROOT);
        return normalized.length() == 3 && normalized.chars().allMatch(Character::isLetter);
    }

    public static List<CurrencyEntity> fetchRange(String symbol, int N) {
        List<CurrencyEntity> histList = new ArrayList<>();
        if (!isValidCurrencyCode(symbol)) {
            return histList;
        }

        int historyDays = normalizeHistoryDays(N);
        String dateEnd = LocalDate.now().format(formatter);
        String dateStart = LocalDate.now().minusDays(historyDays).format(formatter);
        String baseCurrency = Launcher.getBaseCurrency();
        String urlStr = String.format("https://cmu.to/SE233currencyapi?base=%s&symbol=%s&start_date=%s&end_date=%s", baseCurrency, symbol, dateStart, dateEnd);
        try {
            String retrievedJson = IOUtils.toString(new URL(urlStr), Charset.defaultCharset());
            JSONObject jsonOBJ = new JSONObject(retrievedJson).optJSONObject("rates");
            if (jsonOBJ == null || jsonOBJ.isEmpty()) {
                return histList;
            }

            Iterator<String> keysToCopyIterator = jsonOBJ.keys();
            while (keysToCopyIterator.hasNext()) {
                String key = keysToCopyIterator.next();
                Object rawValue = jsonOBJ.opt(key);
                if (rawValue != null) {
                    Double rate = Double.parseDouble(rawValue.toString());
                    histList.add(new CurrencyEntity(rate, key));
                }
            }
            histList.sort(Comparator.comparing(CurrencyEntity::getTimestamp));
        } catch (MalformedURLException e) {
            System.err.println("Encounter a Malformed Url exception");
        } catch (IOException e) {
            System.err.println("Encounter an IO exception");
        } catch (NumberFormatException e) {
            System.err.println("Encountered an invalid rate value in the API response");
        }
        return histList;
    }
}
