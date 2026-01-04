package com.ipaas.monitoringplstformsys.common.utils;


import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtils {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    private static final String PASSWORD_ALLOW =
            CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;

    private static final Random random = new SecureRandom();

    public static String generateStrongPassword(int length) {
        validateLength(length);

        StringBuilder password = new StringBuilder(length);
        addRequiredCharacters(password);
        fillRemainingLength(password, length);

        return shuffleString(password.toString());
    }

    private static void validateLength(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("密码长度至少8位");
        }
    }

    private static void addRequiredCharacters(StringBuilder password) {
        password.append(getRandomChar(CHAR_LOWER))
                .append(getRandomChar(CHAR_UPPER))
                .append(getRandomChar(NUMBER))
                .append(getRandomChar(SPECIAL_CHARS));
    }

    private static char getRandomChar(String charSet) {
        return charSet.charAt(random.nextInt(charSet.length()));
    }

    private static void fillRemainingLength(StringBuilder password, int length) {
        for (int i = password.length(); i < length; i++) {
            password.append(getRandomChar(PASSWORD_ALLOW));
        }
    }

    private static String shuffleString(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int j = random.nextInt(chars.length);
            swap(chars, i, j);
        }
        return new String(chars);
    }

    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
