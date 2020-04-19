package com.assertion.assignment.constants;

import org.springframework.http.HttpStatus;

public class ApplicationConstants
{
    public static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTEUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String SPECIAL_CHARACTERS = "./!@#$%^&*(){}[]<>?;:,";
    public static final String REQ_ERROR_CODE = "PM001";
    public static final String INVALID_LENGTH = "Length can not be less that 0.";
    public static final String TIMESTAMP = "Timestamp";
    public static final String ERROR_CODE = "Error Code";
    public static final String ERROR_DESC = "Error Desc";
    public static final String ALGO_NAME = "SHA-256";
    public static final String SAVE_ERROR_CODE = "PM002";
    public static final String SAVE_ERROR_DESC = "Error occured while saving data.";
    public static final String NO_RECORD_ERROR_CODE = "PM003";
    public static final String NO_RECORD_ERROR_DESC = "No records found in database";
    public static final String DATA_FETCH_ERROR_CODE = "PM004";
    public static final String DATA_FETCH_ERROR_DESC = "Exception while fetching data";
    public static final String INVALID_REQUEST = "PM005";
    public static final String INVALID_REQUEST_DESC = "Request is not valid.";
    public static final String DELETE_CODE = "PM006";
    public static final String DELETE_DESC = "Error occured while deleting the records";
    public static final String DELETE_SUCCESS = "Deleted successfully";
    public static final String UPDATE_SUCCESS = "Updated successfully";
}
