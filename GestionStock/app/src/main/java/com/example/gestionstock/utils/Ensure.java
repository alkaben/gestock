package com.example.gestionstock.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ensure {
    private static final String TAG = "Ensure";
    private static boolean acc = true;
    public static final int NOT_BLANK = 1;
    public static final int IS_EMAIL = 1 << 1;
    public static final int LENGTH = 1 << 2;
    public static final int IS_PHONE_NUMBER = 1 << 3;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_TUNISIAN_PHONE_NUMBER =
            Pattern.compile("^((\\+|00)216)?([2579][0-9]{7}|(3[012]|4[01]|8[0128])[0-9]{6}|42[16][0-9]{5})$");

    public static boolean ensure(String value, int flags) {
        acc = true;
        if ((flags & NOT_BLANK) == NOT_BLANK) {
            acc = acc && (!TextUtils.isEmpty(value));
        }
        if ((flags & IS_EMAIL) == IS_EMAIL) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(value);
            acc = acc && matcher.find();
        }
        if ((flags & IS_PHONE_NUMBER) == IS_PHONE_NUMBER) {
            Matcher matcher = VALID_TUNISIAN_PHONE_NUMBER.matcher(value);
            acc = acc && matcher.find();
        }
        if ((flags & LENGTH) == LENGTH) {
            acc = acc && (value.length() >= 8);
        }
        Log.d(TAG, "ensure: " + acc);
        return acc;
    }

    public static void reset() {
        acc = true;
    }

    public static boolean getCurrentAcc() {
        return acc;
    }
}
