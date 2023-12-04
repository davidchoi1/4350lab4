package com.lab4.validator;

public class BusValidator implements Validator {

    @Override
    public boolean validate(String input) {
        // Check if the input starts with 'B' and is 4 characters long
        return input != null && input.startsWith("B") && input.length() == 4;
    }   
    
}
