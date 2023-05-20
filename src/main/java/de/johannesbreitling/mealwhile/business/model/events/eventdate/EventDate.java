package de.johannesbreitling.mealwhile.business.model.events.eventdate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class EventDate {

    private static final String DATE_PATTERN = "[0-9]{2}-[0-9]{2}-[0-9]{4}(#[0-9]{2}:[0-9]{2})?";

    private int year;

    private int month;

    private int day;

    private int hours;

    private int minutes;

    private static final HashMap<Integer, Integer> MONTHS = new HashMap<>(){{
        put(1, 31);
        put(3, 31);
        put(4, 30);
        put(5, 31);
        put(6, 30);
        put(7, 31);
        put(8, 31);
        put(9, 30);
        put(10, 31);
        put(11, 30);
        put(12, 31);
    }};

    private boolean checkFebruary(int year, int day) {
        var leap = (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
        if (day < 1 || day > 29 || (!leap && day > 28)) {
            throw new IllegalEventDateFormatException();
        }

        return true;
    }

    public EventDate(String date) {
        // Beispiel Date: 25-01-2023#14:12

        if (!date.matches(DATE_PATTERN)) {
            throw new IllegalEventDateFormatException();
        }

        var splitString = date.split("#");

        var splitDate = splitString[0].split("-");

        var day = Integer.parseInt(splitDate[0]);
        var month = Integer.parseInt(splitDate[1]);
        var year = Integer.parseInt(splitDate[2]);

        if (year < 0 || month > 12 || month < 1) {
            throw new IllegalEventDateFormatException();
        }

        if (month != 2 && day > MONTHS.get(month)) {
            throw new IllegalEventDateFormatException();
        }

        if (month == 2 && !checkFebruary(year, day)) {
            throw new IllegalEventDateFormatException();
        }

        this.day = day;
        this.month = month;
        this.year = year;

        if (splitString.length > 1) {
            // Date and Time
            var splitTime = splitString[1].split(":");
            var hours = Integer.parseInt(splitTime[0]);
            var minutes = Integer.parseInt(splitTime[1]);

            if (hours > 24 || hours < 0 || minutes > 59 || minutes < 0) {
                throw new IllegalEventDateFormatException();
            }

            this.minutes = minutes;
            this.hours = hours;
            return;
        }

        this.hours = 12;
        this.minutes = 0;
    }

    @Override
    public String toString() {
        var dayString = day < 10 ? ("0" + day) : (day + "");
        var monthString = month < 10 ? ("0" + month) : (month + "");

        var yearString = year + "";
        if (year < 10) {
            yearString = "000" + year;
        }

        if (year < 100) {
            yearString = "00" + year;
        }

        if (year < 1000) {
            yearString = "0" + year;
        }

        return dayString + "-" + monthString + "-" + yearString;
    }



}
