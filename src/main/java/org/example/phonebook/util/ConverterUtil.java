package org.example.phonebook.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p dir="rtl">
 * کلاس افزونه‌ی تبدیل موجودیت‌ها به موجودیت مناسب را ارائه می‌دهد
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConverterUtil {

    /**
     * <p dir="rtl">
     * در صورت null بودن لیست، یک لیست خالی از همان نوع برمی‌گرداند
     * </p>
     */
    public static <T> List<T> emptyIfNull(List<T> input) {
        return input == null ? new ArrayList<>() : input;
    }
}
