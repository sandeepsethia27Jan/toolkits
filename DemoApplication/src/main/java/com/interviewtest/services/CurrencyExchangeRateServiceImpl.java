package com.interviewtest.services;


import com.interviewtest.cache.OperationalCache;
import com.interviewtest.exceptions.ResourceNotFoundException;
import com.interviewtest.persistence.repository.FileReaderRepository;
import org.bouncycastle.util.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.interviewtest.utils.DummyAppConstants.Constants.FILE_EXTENSION;
import static com.interviewtest.utils.DummyAppConstants.Constants.RESOURCE_LOCATION;
import static com.interviewtest.utils.Messages.ErrorMessages.COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED;

@Service
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeRateServiceImpl.class);
    private FileReaderRepository fileReaderRepository;

    @Autowired
    public CurrencyExchangeRateServiceImpl(FileReaderRepository fileReaderRepository) {
        this.fileReaderRepository = fileReaderRepository;
    }

    @Override
    public List<String> getExchangeRatefromSpecificDate(String date) {
        List<String> lines = new ArrayList<>();

        /*
           first try to find the exchange rates from the cache
           if it is not present in the cache then read it from the files and save it in cache.
         */
        if(OperationalCache.getExchangeRate(date).isPresent()) {
            lines = OperationalCache.getExchangeRate(date).get();
        }else{
            if (confirmIfFilePresent(date)) {
                try {
                    lines = fileReaderRepository.readSingleFileData(ResourceUtils.getFile(getClass().getClassLoader().getResource(RESOURCE_LOCATION + date + FILE_EXTENSION)).getAbsolutePath());
                    OperationalCache.put(date, lines);
                } catch (FileNotFoundException e) {
                    LOGGER.error(COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED);
                }
            } else {
                throw new ResourceNotFoundException(COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED);
            }
        }
        return lines;
    }

    @Override
    public String findExchangeRateBetweenCurrency(String date, String curr1, String curr2) throws FileNotFoundException {
        List<String> exchangeRates = new ArrayList<>();
        if(OperationalCache.getExchangeRate(date).isPresent()) {
            exchangeRates = OperationalCache.getExchangeRate(date).get();
        } else {
            if (confirmIfFilePresent(date)) {
                exchangeRates = fileReaderRepository.readSingleFileData(ResourceUtils.getFile(getClass().getClassLoader().getResource(RESOURCE_LOCATION + date + FILE_EXTENSION)).getAbsolutePath());
                OperationalCache.put(date, exchangeRates);
            } else {
                LOGGER.error(COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED);
                throw new ResourceNotFoundException(COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED);
            }
        }
        exchangeRates.stream()
                .filter(x -> !x.contains(curr1) || !x.contains(curr2))
                .collect(Collectors.toList());

        return calculateExchangeRatesBetweenCurrencies(exchangeRates, curr1, curr2);
    }


    @Override
    public List<String> getExchangeRateOfSpecificCurrency(String startDate, String endDate, String currency) throws IOException {

        //TODO: implement the OperationalCache service in same way as the other two api methods in this class
        return fileReaderRepository.readExchangeRateOverTimeRange(startDate, endDate, currency);
    }

    public boolean confirmIfFilePresent(String date) {
        try {
            String fileLocation = getClass().getClassLoader().getResource(RESOURCE_LOCATION + date + FILE_EXTENSION).getFile();
            return new File(fileLocation).exists();
        } catch (Exception ex) {
            return false;
        }
    }

    private String calculateExchangeRatesBetweenCurrencies(List<String> exchangeRates, String curr1, String curr2) {
        String currency1 = null;
        String currency2 = null;

        while (null == currency1 && null == currency2) {
            for (String string : exchangeRates) {
                if (string.contains(curr1)) {
                    currency1 = string;
                } else if (string.contains(curr2)) {
                    currency2 = string;
                }
            }
        }

        String[] array1 = Strings.split(currency1, ' ');
        String[] array2 = Strings.split(currency2, ' ');

        float car1 = Float.parseFloat(array1[4]);
        float car2 = Float.parseFloat(array2[4]);

        return " 1 " + curr1 + " traded at " + car1 / car2 + " times " + curr2;
    }

    protected boolean checkProperDateFormat() {

        return false;
    }

    private void cacheOperationalData(String date, List<String> exchangeRates) {
        OperationalCache.put(date, exchangeRates);

    }
}
