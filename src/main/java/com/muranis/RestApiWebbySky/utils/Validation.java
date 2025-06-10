package com.muranis.RestApiWebbySky.utils;

import com.muranis.RestApiWebbySky.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidPassword(String password) {
        boolean length = password.length() >= 8 && password.length() <= 24;
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasSpecialChar = password.chars().anyMatch(c -> "!@#$%^&*()-_+=?.,<>\\/|[]{}:;\"'".indexOf(c) != -1);
        return length && hasDigit && hasUpperCase && hasLowerCase && hasSpecialChar;
    }

    public static boolean isValidUsername(String username) {
        String usernameRegex = "^[0-9A-Za-z]{6,16}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
