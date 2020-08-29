package org.example.phonebook.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtil {
    /**
     * <p dir="rtl">
     * الگوی در نظرگرفته شده برای فرمت رایانامه را ارائه می‌دهد
     * </p>
     */
    private static final String EMAIL_REGEX =
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    /**
     * <p dir="rtl">
     * خالی بودن رشته ورودی را برمی‌گرداند
     * </p>
     */
    public static boolean isEmptyString(final String input) {
        return input == null || input.isEmpty();
    }

    /**
     * <p dir="rtl">
     * منطبق بودن آدرس رایانامه بر الگوی درنظرگرفته شده را برمی‌گرداند
     * </p>
     */
    public static boolean isValidEmailAddress(final String emailAddress) {
        return !isEmptyString(emailAddress) && emailAddress.matches(EMAIL_REGEX);
    }
}
