package com.interviewtest.controller;


/**
 * created by ssethia on 5/26/2018.
 */

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Api(value = "/currency", tags = "Currency")
public interface SpringControllerApi {
    @ApiOperation(
            value = "Get Exchange Rate Of All Currencies on a Date",
            notes = "Retrieves List of all the exchange rates of all currencies on a specific date",
//            authorizations = {@Authorization(value = "Valid JWT")}, (Spring JWT-Security can be implemented here to use a valid Jwt authentication)
            response = String.class,
            responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code = 400, message = "DATE IS REQUIRED")
    })
    List<String> getExchangeRatesSpecificToDate(
            @ApiParam(value = "Date Specified", required = true) String date
    );

    @ApiOperation(
            value = "Find the exchange rate between currencies on a date",
            notes = "find the exchange rates between currencies",
            response = String.class
//            authorizations = {@Authorization(value = "Valid JWT")}, (Spring JWT-Security can be implemented here to use a valid Jwt authentication)
    )
    @ApiResponses({
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code = 400, message = "PARAMETERS ARE REQUIRED")
    })
    ResponseEntity<String> findExchangeRatesBetweenCurrenciesOnSpecificDate(
            @ApiParam(value = "Setting Name", required = true) String date,
            @ApiParam(value = "Currency 1", required = true) String currency1,
            @ApiParam(value = "Currency 2", required = true) String currency2
    ) throws FileNotFoundException;

    @ApiOperation(
            value = "Find the exchange rate of a currency between a date range",
            notes = "find the exchange rates of a currency over a time",
            response = String.class,
            responseContainer = "List"
//            authorizations = {@Authorization(value = "Valid JWT")}, (Spring JWT-Security can be implemented here to use a valid Jwt authentication)
    )
    @ApiResponses({
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code = 400, message = "PARAMETERS ARE REQUIRED")
    })
    List<String> findExchangeRatesForCurrencyInDateRange(
            @ApiParam(value = "Setting Name", required = true) String date,
            @ApiParam(value = "Currency 1", required = true) String currency1,
            @ApiParam(value = "Currency 2", required = true) String currency2
    ) throws IOException;


}
