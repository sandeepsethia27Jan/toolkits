package com.interviewtest.utils;

public enum Messages {
    ;

    public static class ErrorMessages {
        public static final String COUND_NOT_FIND_THE_FILE_FOR_DATE_SPECIFIED = "Could not find the file for the date specified";
        public static final String IO_EXCEPTION_DURING_FILE_READING = "file could not be read properly.";
        public static final String DATE_IS_MANDATORY = "Date is required for this API";

    }

    public static class SuccessfulMessages {
        public static final String EMPTY_MESSAGE = "";
        public static final String AUTHENTICATOR_REMOVED_SUCCESSFULLY = "Authenticator was removed successfully.";
    }
}
