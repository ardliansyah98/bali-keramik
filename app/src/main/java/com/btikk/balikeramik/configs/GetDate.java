package com.btikk.balikeramik.configs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
    public String returnDate(String dateCreated) {
        DateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat output = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        try {
            Date date = input.parse(dateCreated);
            String outputDate = output.format(date);
            return outputDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
