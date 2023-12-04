package com.lab4.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator implements Validator {
    @Override
    public boolean validate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Set lenient to false to enforce strict parsing

        try {
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

