package com.ahmetgeze.flightticket.utils;

import java.util.Arrays;

public class UtilsFunc {
   public static boolean isNotNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return false;
            }
        }
        return true;

    }

}
