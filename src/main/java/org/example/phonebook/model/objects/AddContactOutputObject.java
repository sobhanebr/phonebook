package org.example.phonebook.model.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p dir="rtl">
 * این کلاس خروجی برای اضافه کردن مخاطب جدید استفاده می‌شود
 * </p>
 */
@Data
@AllArgsConstructor
public class AddContactOutputObject {
    private Integer id;
    private String fullName;
}
