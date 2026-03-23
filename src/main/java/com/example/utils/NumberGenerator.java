package com.example.utils;

import java.security.SecureRandom;
import java.time.LocalDate;

public class NumberGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generateAccountNumber(int digits) {
        if (digits < 6) {
            throw new IllegalArgumentException("Mínimo 6 dígitos");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(1 + random.nextInt(9));

        for (int i = 1; i < digits; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static String generateReference() {
        String date = LocalDate.now().toString().replace("-", "");
        int random = new SecureRandom().nextInt(999999);
        return "TRX-" + date + "-" + String.format("%06d", random);
    }
}
