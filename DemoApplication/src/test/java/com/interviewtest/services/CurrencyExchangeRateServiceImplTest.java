package com.interviewtest.services;


import com.interviewtest.persistence.repository.FileReaderRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CurrencyExchangeRateServiceImplTest {


    //TODO: implement unit test (Same like springControllerTest)

    @InjectMocks
    private CurrencyExchangeRateServiceImpl currencyExchangeRateService;

    @Mock
    private FileReaderRepositoryImpl fileReaderRepositoryImpl;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_confirmIfFilePresent_should_return_ok_if_file_present() {
        String date = "2017-01-02";
        Assert.assertTrue(currencyExchangeRateService.confirmIfFilePresent(date));
    }

    @Test
    public void test_getExchangeRatefromSpecificDate_should_reutrn_a_list_of_exchange_rates_for_the_date_mentioned() {

    }

    @Test
    public void test_getExchangeRatefromSpecificDate_should_throw_exception_when_given_date_is_not_in_proper_format() {

    }

    @Test
    public void test_findExchangeRateBetweenCurrency_should_give_a_string_of_currency_exchnage() {

    }

    @Test
    public void test_getExchangeRateOfSpecificCurrency_should_return_a_list_of_String() {

    }


}
