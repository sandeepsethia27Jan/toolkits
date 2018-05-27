package com.interviewtest.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.List;
import java.util.Optional;

public class OperationalCache {

    public static final Cache<String, List<String>> cache = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .maximumSize(10000)
            .build();

    public OperationalCache() {
    }

    public static void put (String date, List<String> exchangeRates) {
        if(null != date && null != exchangeRates) {
            cache.put(date, exchangeRates);
        }
    }

    public static Optional<List<String>> getExchangeRate(String date) {
        if(null != date) {
            return Optional.ofNullable(cache.getIfPresent(date));
        }
        return Optional.empty();
    }

    public static void invalidate(String date) {
        if(null != date) {
            cache.invalidate(date);
        }
    }
}
