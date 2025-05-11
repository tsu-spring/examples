package com.example.util;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;

public class TimeFormatter {

    private static final PrettyTime prettyTime = new PrettyTime();

    public static String prettyFormat(LocalDateTime localDateTime) {
        return prettyTime.format(localDateTime);
    }
}
