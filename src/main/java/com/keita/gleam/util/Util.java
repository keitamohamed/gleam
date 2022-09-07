package com.keita.gleam.util;

import java.util.Random;

public class Util {

    public static Long generateID(int bound) {
        Random random = new Random();
        return (long) random.nextInt(bound);
    }

    public static String generateSixDigit() {
        Random random = new Random();
        int num = random.nextInt(999999);
        return String.format("%06d", num);
    }
}
