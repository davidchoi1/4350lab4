package com.lab4.validator;

public class TripValidator implements Validator {
    @Override
    public boolean validate(String tripNumberString) {
        if (tripNumberString == null || tripNumberString.isEmpty()) {
            return false;
        }

        // Regular expression to match a 4-digit number
        String tripNumberPattern = "\\d{4}";
        return tripNumberString.matches(tripNumberPattern);
    }
}
