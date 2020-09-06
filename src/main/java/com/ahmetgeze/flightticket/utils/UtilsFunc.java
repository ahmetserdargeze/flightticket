package com.ahmetgeze.flightticket.utils;

import java.util.Arrays;
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

    public static String toUpperCaseWithTurkishCharacter(String input){
       if (isNotNull(input)){
           return input.toUpperCase(new Locale("tr","TR"));
       }else{
           return "";
       }

    }

}
