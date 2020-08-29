package org.example.phonebook.model.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * <p dir="rtl">
 * شیء خروجی جستجو میان مخاطبین را ارائه می‌دهد
 * </p>
 */
@Data
@AllArgsConstructor
public class SearchResultOutputObject {
    private int totalCount;
    private int filterCount;
    private List<ContactOutputObject> contactObjects;
}
