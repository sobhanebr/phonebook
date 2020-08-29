package org.example.phonebook.util.exceptions;

import lombok.EqualsAndHashCode;

/**
 * <p dir="rtl">
 * خطای وجود پارامتر اشتباه را ارائه می‌دهد
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
public class InputParamException extends GenericApplicationException {
    public InputParamException(String message) {
        super(message, 601);
    }
}
