package org.example.phonebook.util.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p dir="rtl">
 * شیء خطاهای شناخته شده در برنامه را ارائه می‌دهد
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenericApplicationException extends RuntimeException {
    private final Integer code;

    public GenericApplicationException(Throwable cause) {
        super(cause);
        this.code = ExceptionConstants.PROCESSING_ERROR_CODE;
    }

    protected GenericApplicationException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public GenericApplicationException(String message) {
        super(message);
        this.code = ExceptionConstants.PROCESSING_ERROR_CODE;
    }
}
