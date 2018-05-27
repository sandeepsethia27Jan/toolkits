package com.interviewtest.persistence.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class FileReaderRepositoryImplTest {


    //TODO: implement unit test (Same like springControllerTest)

    @InjectMocks
    private FileReaderRepositoryImpl fileReaderRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_readSingleFileData_should_give_a_list_of_string_with_all_lines_of_file() {

    }

    @Test
    public void test_readExchangeRateOverTimeRange_should_find_a_string_from_multiple_file_and_return_the_list() {

    }


}
