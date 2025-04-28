package com.muranis.RestApiWebbySky.utils;

import com.muranis.RestApiWebbySky.model.User;

public class Validation {
    public static boolean isValidPassword(String password) {
        boolean length = password.length() >= 8 && password.length() <= 24;
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasSpecialChar = password.chars().anyMatch(c -> "!@#$%^&*()-_+=?.,<>\\/|[]{}:;\"'".indexOf(c) != -1);
        return length && hasDigit && hasUpperCase && hasLowerCase && hasSpecialChar;
    }
}
