package com.interviewtest.services;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CurrencyExchangeRateService {


    /**
     *
     * @param date
     * @return
     */
    List<String> getExchangeRatefromSpecificDate(String date);

    /**
     *
     * @param date
     * @param curr1
     * @param curr2
     * @return String
     * @throws FileNotFoundException
     */
    String findExchangeRateBetweenCurrency(String date, String curr1, String curr2) throws FileNotFoundException;

    /**
     *
     * @param startDate
     * @param endDate
     * @param currency
     * @return
     * @throws IOException
     */
    List<String> getExchangeRateOfSpecificCurrency(String startDate, String endDate, String currency) throws IOException;
}
