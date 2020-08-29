package org.example.phonebook.view;

import org.example.phonebook.controller.ContactController;
import org.example.phonebook.model.objects.AddContactOutputObject;
import org.example.phonebook.model.objects.ContactObject;
import org.example.phonebook.model.objects.SearchResultOutputObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p dir="rtl">
 * واسط‌های مربوط به مخاطبین را ارائه می‌دهد
 * </p>
 */
@RestController
@RequestMapping("/contacts")
public class ContactRests {

    private final ContactController contactController;

    @Autowired
    public ContactRests(ContactController contactController) {
        this.contactController = contactController;
    }

    /**
     * <p dir="rtl">
     * واسط مربوط به اضافه کردن مخاطب را ارائه می‌دهد
     * </p>
     */
    @PutMapping
    public ResponseEntity<AddContactOutputObject> add(@RequestBody ContactObject contactObject) {
        return contactController.add(contactObject);
    }

    /**
     * <p dir="rtl">
     * واسط مربوط به جستجو میان مخاطبین را ارائه می‌دهد
     * </p>
     */
    @PostMapping("/search")
    public ResponseEntity<SearchResultOutputObject> search(@RequestBody ContactObject contactObject) {
        return contactController.search(contactObject);
    }

}
