package org.example.phonebook.dao;

import org.example.phonebook.model.to.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p dir="rtl">
 * ارتباط با پایگاه‌داده جهت امکان خوانش و نوشتار موجودیت
 * {@link ContactEntity}
 * را فراهم می‌آورد
 * </p>
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {
    /**
     * <p dir="rtl">
     * وجود مخاطب با آدرس رایانامه‌ی مدنظر را برمی‌گرداند
     * </p>
     *
     * @param emailAddress آدرس رایانامه‌ی مدنظر
     */
    boolean existsByEmailAddress(final String emailAddress);

    /**
     * <p dir="rtl">
     * وجود مخاطب با شماره تلفن مدنظر را برمی‌گرداند
     * </p>
     *
     * @param phoneNumber شماره تلفن مدنظر
     */
    boolean existsByPhoneNumber(final String phoneNumber);

    /**
     * <p dir="rtl">
     * وجود مخاطب با نام کاربری GitHub مدنظر را برمی‌گرداند
     * </p>
     *
     * @param githubAccountName نام کاربری GitHub مدنظر
     */
    boolean existsByGithubAccountName(final String githubAccountName);

    /**
     * <p dir="rtl">
     * وجود مخاطب با نام مدنظر را برمی‌گرداند
     * </p>
     *
     * @param fullName نام مدنظر
     */
    boolean existsByFullName(final String fullName);

}
