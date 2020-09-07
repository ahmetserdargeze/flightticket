package com.ahmetgeze.flightticket.utils;

import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class UtilsFunc {
    public static boolean isNotNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return false;
            }
        }
        return true;

    }

    public static String toUpperCaseWithTurkishCharacter(String input) {
        if (isNotNull(input)) {
            return input.toUpperCase(new Locale("tr", "TR"));
        } else {
            return "";
        }

    }

    public static Date getNextDayDateFromDate(Date date) {
        try {
            Date nextDay = DateUtils.addDays(date, 1);
            return nextDay;
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.UTIL_EXCEPTION, ExceptionCode.NEXT_DATE_CONVERT_ERR1, e));
        }
    }

}
