package com.interviewtest.controller;


import com.google.common.base.Strings;
import com.interviewtest.services.CurrencyExchangeRateService;
import com.interviewtest.web.infra.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.interviewtest.utils.Messages.ErrorMessages.DATE_IS_MANDATORY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by ssethia on 5/26/2018.
 */
@RestController
@RequestMapping(value = "v1")
public class SpringController implements SpringControllerApi{


    private CurrencyExchangeRateService currencyExchangeRateService;

    @Autowired
    public SpringController(CurrencyExchangeRateService currencyExchangeRateService) {
        this.currencyExchangeRateService = currencyExchangeRateService;
    }

    @Override
    @RequestMapping(method = GET, value = "/exchangeRate", produces = APPLICATION_JSON_VALUE)
    public List<String> getExchangeRatesSpecificToDate(
            @RequestParam(value = "date", required = true) String date
    ) {
        checkArgument(!Strings.isNullOrEmpty(date), DATE_IS_MANDATORY);

        return currencyExchangeRateService.getExchangeRatefromSpecificDate(date);
    }

    @Override
    @RequestMapping(method = GET, value = "/exchangeRateOnDate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findExchangeRatesBetweenCurrenciesOnSpecificDate(
            @RequestParam(value = "date", required = true) String date,
            @RequestParam(value = "currency1", required = true) String currency1,
            @RequestParam(value = "currency2", required = true) String currency2
    ) throws FileNotFoundException {
        checkArgument(!Strings.isNullOrEmpty(date), "date is required for this API");
        checkArgument(!Strings.isNullOrEmpty(currency1), "currency code1 is required for this");
        checkArgument(!Strings.isNullOrEmpty(currency2), "currency code2 is required for this");
        return Responses.ok(currencyExchangeRateService.findExchangeRateBetweenCurrency(date, currency1, currency2));
    }

    @Override
    @RequestMapping(method = GET, value = "/exchangeRateOverTimeRange", produces = APPLICATION_JSON_VALUE)
    public List<String> findExchangeRatesForCurrencyInDateRange(
            @RequestParam(value = "startingDate", required = true) String startDate,
            @RequestParam(value = "endingDate", required = true) String endDate,
            @RequestParam(value = "currency", required = true) String currency

    ) throws IOException {
        checkArgument(!Strings.isNullOrEmpty(startDate), "startDate is required for this API");
        checkArgument(!Strings.isNullOrEmpty(endDate), "endDate is required for this");
        checkArgument(!Strings.isNullOrEmpty(currency), "currency code is required for this");

        return currencyExchangeRateService.getExchangeRateOfSpecificCurrency(startDate, endDate, currency);

    }

}
