package com.lab4.validator;

public class TimeValidator implements Validator {
    @Override
    public boolean validate(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            return false;
        }

        // Regular expression to match the time format HH:MM:SS (24-hour format)
        String timePattern = "([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)";
        return timeString.matches(timePattern);
    }
}

