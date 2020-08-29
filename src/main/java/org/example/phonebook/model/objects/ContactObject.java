package org.example.phonebook.model.objects;

import lombok.Data;
import org.example.phonebook.util.exceptions.GenericApplicationException;
import org.example.phonebook.util.exceptions.InputParamException;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.example.phonebook.util.ValidatorUtil.isEmptyString;
import static org.example.phonebook.util.ValidatorUtil.isValidEmailAddress;

/**
 * <p dir="rtl">
 * این کلاس ورودی برای اضافه کردن مخاطب یا جستجو میان آنان استفاده می‌شود
 * </p>
 */
@Data
public class ContactObject {
    private String name;
    private String phoneNumber;
    private String email;
    private String organization;
    private String github;

    /**
     * <p dir="rtl">
     * مقادیر پارامترهای شیء را ارزیابی می‌کند
     * و در صورت وجود پارامتری null خطای پارامتر صادر می‌کند
     * </p>
     */
    public void validate() {
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> {
                    try {
                        return isEmptyString((String) field.get(this));
                    } catch (IllegalAccessException e) {
                        throw new GenericApplicationException(e);
                    }
                }).findAny().ifPresent(field -> {
            throw new InputParamException("Empty " + field.getName());
        });
        if (!isValidEmailAddress(email))
            throw new InputParamException("Invalid email :" + email);
    }
}