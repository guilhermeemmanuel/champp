package com.asus.embedded.champp.model;

public class Util {

    public static int getNearLowPotency(int number, int limit) {
        if(number > limit) {
            return -1;
        }
        else if(number == limit) {
            return number;
        }

        int i = 2;
        while(true) {
            if(Math.pow(number,i) > limit) {
                return (int)(Math.pow(number, i - 1));
            }
            i++;
        }
    }

}
