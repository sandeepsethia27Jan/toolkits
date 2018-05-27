package com.interviewtest.persistence.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.interviewtest.utils.DummyAppConstants.Constants.FILE_EXTENSION;
import static com.interviewtest.utils.DummyAppConstants.Constants.RESOURCE_LOCATION;
import static com.interviewtest.utils.Messages.ErrorMessages.COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED;
import static com.interviewtest.utils.Messages.ErrorMessages.IO_EXCEPTION_DURING_FILE_READING;

@Service
public class FileReaderRepositoryImpl implements FileReaderRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderRepositoryImpl.class);


    @Override
    public List<String> readSingleFileData(String fileName) {

        List<String> exchangeRates = null;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            exchangeRates = stream.collect(Collectors.toList());

        } catch (IOException e) {
           LOGGER.error(IO_EXCEPTION_DURING_FILE_READING);
        }
        return exchangeRates;
    }

    @Override
    public List<String> readExchangeRateOverTimeRange(String startDate, String endDate, String currency) throws IOException {
        LocalDate sDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate eDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        List<LocalDate> totalDates = new ArrayList<>();
        while (!sDate.isAfter(eDate)) {
            totalDates.add(sDate);
            sDate = sDate.plusDays(1);
        }

        List<String> files = totalDates.stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());

        List<String> finalList = new ArrayList<>();

        for (String filename : files) {
            try (Stream<String> lines = Files.lines(Paths.get(
                    ResourceUtils.getFile(
                            getClass().getClassLoader().getResource(RESOURCE_LOCATION + filename + FILE_EXTENSION))
                            .getAbsolutePath()))) {
                List<String> line = lines.filter(x -> x.contains(currency))
                        .map(x-> "On " +filename + " - " +x)
                        .collect(Collectors.toList());
                finalList.addAll(line);
            } catch (IOException ex) {
                LOGGER.error(COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED);
            }
        }
        return finalList;
    }

}

