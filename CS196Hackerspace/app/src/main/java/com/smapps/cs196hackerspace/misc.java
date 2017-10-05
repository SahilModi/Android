package com.smapps.cs196hackerspace;

/**
 * Created by Sahil on 10/4/2017.
 */

public class misc {

    public static double divide(double a, double b) {
        if (b == 0) {
            return 365.1;
        } else {
            return a / b;
        }
    }

    public static boolean isEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            String temp = email.substring(0, email.indexOf("@"))
                    + email.substring(email.indexOf("@") + 1, email.length());
            if(!temp.contains("@")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
