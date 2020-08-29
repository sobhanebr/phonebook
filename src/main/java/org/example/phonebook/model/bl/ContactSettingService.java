package org.example.phonebook.model.bl;

import lombok.extern.slf4j.Slf4j;
import org.example.phonebook.dao.ContactRepository;
import org.example.phonebook.dao.GeneralDao;
import org.example.phonebook.model.objects.AddContactOutputObject;
import org.example.phonebook.model.objects.ContactObject;
import org.example.phonebook.model.objects.ContactOutputObject;
import org.example.phonebook.model.objects.SearchResultOutputObject;
import org.example.phonebook.model.to.ContactEntity;
import org.example.phonebook.util.exceptions.DuplicateUniqueValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p dir="rtl">
 * سرویس تنظیمات مخاطبین را ارائه می‌دهد
 * </p>
 */
@Slf4j
@Service
public class ContactSettingService {

    private final ContactRepository contactRepository;
    private final GeneralDao generalDao;
    private final RepositoryDiscover repositoryDiscover;

    @Autowired
    public ContactSettingService(ContactRepository contactRepository, GeneralDao generalDao,
                                 RepositoryDiscover repositoryDiscover) {
        this.contactRepository = contactRepository;
        this.generalDao = generalDao;
        this.repositoryDiscover = repositoryDiscover;
    }

    /**
     * <p dir="rtl">
     * در صورت عدم وجود مقدار تکراری در پارامترهای شیء ورودی، مخاطبی جدید با اطلاعات ورودی را ایجاد کرده ذخیره می‌کند
     * </p>
     *
     * @param contactObject شیء ورودی مخاطب
     * @return {@link AddContactOutputObject} شیء نام و مشخصه‌ی مخاطب ایجاد شده
     */
    public AddContactOutputObject add(ContactObject contactObject) {
        checkForDuplicate(contactObject);
        ContactEntity contact = ContactEntity.ofAddContactInputObject(contactObject);
        contactRepository.save(contact);
        log.info("Contact[{}] was saved successfully", contact);
        repositoryDiscover.addUndiscoveredContact(contact);
        return new AddContactOutputObject(contact.getId(), contact.getFullName());
    }

    /**
     * <p dir="rtl">
     * در صورت وجود پارامتری یکتا با مقدار تکراری خطای مرتبط را صادر می‌کند
     * </p>
     *
     * @param contactObject شیء ورودی مخاطب
     */
    private void checkForDuplicate(ContactObject contactObject) {
        if (contactRepository.existsByEmailAddress(contactObject.getEmail()))
            throw new DuplicateUniqueValueException("email: " + contactObject.getEmail());
        if (contactRepository.existsByPhoneNumber(contactObject.getPhoneNumber()))
            throw new DuplicateUniqueValueException("phone number: " + contactObject.getPhoneNumber());
        if (contactRepository.existsByGithubAccountName(contactObject.getGithub()))
            throw new DuplicateUniqueValueException("github name: " + contactObject.getGithub());
        if (contactRepository.existsByFullName(contactObject.getName()))
            throw new DuplicateUniqueValueException("name: " + contactObject.getName());
    }

    /**
     * <p dir="rtl">
     * با توجه به شیء ورودی، تمامی مخاطبین منطبق را برمی‌گرداند
     * </p>
     *
     * @param contactObject شیء ورودی مخاطب
     * @return {@link SearchResultOutputObject}
     */
    public SearchResultOutputObject search(ContactObject contactObject) {
        final int totalCount = (int) contactRepository.count();
        List<ContactEntity> searchResult = generalDao.loadAll(contactObject);
        return new SearchResultOutputObject(totalCount,
                searchResult.size(),
                searchResult.parallelStream().map(ContactOutputObject::ofContactEntity).collect(Collectors.toList()));
    }
}
