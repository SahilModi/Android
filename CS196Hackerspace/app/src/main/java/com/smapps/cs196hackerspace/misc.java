package com.smapps.cs196hackerspace;

/**
 * Created by Sahil on 10/4/2017.
 */

public class misc {

    public static double divide(double a, double b){
        if(b == 0 ){
            return 365.1;
        } else {
            return a/b;
        }
    }

    public static boolean isEmail(String email){
        if(email.contains("@") && email.contains(".")){
            return true;
        } else {
            return false;
        }

    }

}
