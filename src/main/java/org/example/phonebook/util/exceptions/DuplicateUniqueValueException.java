package org.example.phonebook.util.exceptions;

import lombok.EqualsAndHashCode;

/**
 * <p dir="rtl">
 * خطای درخواست درج مخاطب با پارامتر تکراری ارائه می‌دهد
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
public class DuplicateUniqueValueException extends GenericApplicationException {
    public DuplicateUniqueValueException(String message) {
        super(message, 602);
    }
}
