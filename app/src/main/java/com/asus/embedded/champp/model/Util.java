package com.asus.embedded.champp.model;

import java.util.List;
import java.util.Random;

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


    public static List<Participant> suffle(List<Participant> participantList ) {
        Random random = new Random();
        for (int i = 0; i < participantList.size() ; i++) {
            int position = random.nextInt(participantList.size() -1);
            Participant p = participantList.remove(position);
            participantList.add(p);
        }
        return participantList;
    }

}
