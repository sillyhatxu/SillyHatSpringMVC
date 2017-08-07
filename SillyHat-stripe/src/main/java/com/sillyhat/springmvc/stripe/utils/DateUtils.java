package com.sillyhat.springmvc.stripe.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    public static String getToday(){
        return sdfDate.format(new Date());
    }
}
