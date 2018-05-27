package com.interviewtest.controller;

import com.interviewtest.services.CurrencyExchangeRateService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class SpringControllerTest {

    private static final String DATE = "2017-01-01";
    private static final String DATE1 = "2017-01-05";
    private static final String CURRENCY1 = "SGD";
    private static final String CURRENCY2 = "EUR";

    @InjectMocks
    private SpringController springController;

    @Mock
    private CurrencyExchangeRateService currencyExchangeRateService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getExchangeRatesSpecificToDate_should_return_List_of_exchange_rates_if_the_file_exist() {
        when(currencyExchangeRateService.getExchangeRatefromSpecificDate(anyString())).thenReturn(prepareMockedList());
        List<String> exchangeRates = springController.getExchangeRatesSpecificToDate(DATE);
        assertThat(exchangeRates).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getExchangeRatesSpecificToDate_should_throw_Exception_if_the_Date_is_not_passed_correctly() {
        List<String> exchangeRates = springController.getExchangeRatesSpecificToDate("");
        Assertions.assertThatIllegalArgumentException();
    }

    @Test
    public void test_findExchangeRatesBetweenCurrenciesOnSpecificDate_should_reutrn_currency_exchange_string_if_the_file_and_currencies_are_ok() throws FileNotFoundException {
        when(currencyExchangeRateService.findExchangeRateBetweenCurrency(anyString(), anyString(), anyString())).thenReturn("proper");
        final ResponseEntity<String> result = springController.findExchangeRatesBetweenCurrenciesOnSpecificDate(DATE, CURRENCY1, CURRENCY2);
        assertThat(result).isNotNull()
                .returns(HttpStatus.OK, ResponseEntity::getStatusCode)
                .returns("proper", ResponseEntity::getBody);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_findExchangeRatesBetweenCurrenciesOnSpecificDate_should_throw_exception_if_the_Date_is_not_ok() throws FileNotFoundException {
        final ResponseEntity<String> result = springController.findExchangeRatesBetweenCurrenciesOnSpecificDate("", CURRENCY1, CURRENCY2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_findExchangeRatesBetweenCurrenciesOnSpecificDate_should_throw_exception_if_the_Currency1_is_not_ok() throws FileNotFoundException {
        final ResponseEntity<String> result = springController.findExchangeRatesBetweenCurrenciesOnSpecificDate(DATE, "", CURRENCY2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_findExchangeRatesBetweenCurrenciesOnSpecificDate_should_throw_exception_if_currency2_is_not_ok() throws FileNotFoundException {
        final ResponseEntity<String> result = springController.findExchangeRatesBetweenCurrenciesOnSpecificDate(DATE, CURRENCY1, "");
    }

    @Test
    public void test_findExchangeRatesForCurrencyInDateRange_should_return_list_of_exchange_rate_for_the_given_currency_between_given_dates() throws IOException {
        when(currencyExchangeRateService.getExchangeRateOfSpecificCurrency(anyString(), anyString(), anyString())).thenReturn(prepareMockedList());
        List<String> exchangeRates = springController.findExchangeRatesForCurrencyInDateRange(DATE, DATE1, CURRENCY1);
        assertThat(exchangeRates).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_findExchangeRatesForCurrencyInDateRange_should_throw_error_if_currency_passed_is_not_ok() throws IOException {
        springController.findExchangeRatesForCurrencyInDateRange(DATE, DATE1, "");
    }

    private static List<String> prepareMockedList() {
        return asList(
                "Dummy1",
                "Dummy2");
    }
}
