package org.example.phonebook.util.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p dir="rtl">
 * جهت دسترسی به ثابت خطاهای ناشناخته‌ی برنامه استفاده می‌شود
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstants {
    public static final Integer PROCESSING_ERROR_CODE = 600;
}