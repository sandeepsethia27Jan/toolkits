package com.interviewtest.persistence.repository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface FileReaderRepository {

    List<String> readSingleFileData(String fileName);

    List<String> readExchangeRateOverTimeRange(String startDate, String endDate, String currency) throws IOException;

}
