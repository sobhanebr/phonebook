package org.example.phonebook.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.phonebook.model.bl.ContactSettingService;
import org.example.phonebook.model.objects.AddContactOutputObject;
import org.example.phonebook.model.objects.ContactObject;
import org.example.phonebook.model.objects.SearchResultOutputObject;
import org.example.phonebook.util.exceptions.GenericApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import static org.example.phonebook.util.exceptions.ExceptionConstants.PROCESSING_ERROR_CODE;

/**
 * <p dir="rtl">
 * این واسط برای ارزیابی ورودی‌های rest و سپس فرخوانی سرویس مربوطه ایجاد شده است
 * </p>
 */
@Slf4j
@Controller
public class ContactController {

    private final ContactSettingService contactSettingService;

    @Autowired
    public ContactController(ContactSettingService contactSettingService) {
        this.contactSettingService = contactSettingService;
    }

    /**
     * <p dir="rtl">
     * ورودی برای افزودن مخاطب را ارزیابی کرده مخاطب را ایجاد کرده نام و مشخصه‌اش را برمی‌گرداند
     * </p>
     */
    public ResponseEntity<AddContactOutputObject> add(ContactObject contactObject) {
        try {
            contactObject.validate();
            return ResponseEntity.ok(contactSettingService.add(contactObject));
        } catch (GenericApplicationException e) {
            log.error("Unsuccessful contact insertion operation", e);
            return ResponseEntity.status(e.getCode()).build();
        } catch (Exception e) {
            log.error("Error while adding new contact", e);
            return ResponseEntity.status(PROCESSING_ERROR_CODE).build();
        }
    }

    /**
     * <p dir="rtl">
     * عمل جستجو در میان مخاطبین را انجام داده و موارد منطبق را در خروجی برمی‌گرداند
     * </p>
     */
    public ResponseEntity<SearchResultOutputObject> search(ContactObject contactObject) {
        try {
            return ResponseEntity.ok(contactSettingService.search(contactObject));
        } catch (Exception e) {
            log.error("Error while adding new contact", e);
            return ResponseEntity.status(PROCESSING_ERROR_CODE).build();
        }
    }
}
