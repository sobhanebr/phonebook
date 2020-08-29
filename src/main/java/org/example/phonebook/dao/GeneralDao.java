package org.example.phonebook.dao;

import org.example.phonebook.model.objects.ContactObject;
import org.example.phonebook.model.to.ContactEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p dir="rtl">
 * ارتباط با پایگاه‌داده جهت امکان جستجو میان موجودیت
 * {@link ContactEntity}
 * را فراهم می‌آورد
 * </p>
 */
@Component
public class GeneralDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * <p dir="rtl">
     * بر اساس مقادیر شیء پارامتر تمامی موجودیت‌های منطبق را برمی‌گرداند
     * </p>
     *
     * @param contactObject {@link ContactObject}
     */
    public List<ContactEntity> loadAll(ContactObject contactObject) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContactEntity> query = cb.createQuery(ContactEntity.class);
        Root<ContactEntity> contactEntityRoot = query.from(ContactEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (contactObject.getEmail() != null) {
            Path<String> emailPath = contactEntityRoot.get("emailAddress");
            predicates.add(cb.like(emailPath, contactObject.getEmail()));
        }
        if (contactObject.getGithub() != null) {
            Path<String> githubPath = contactEntityRoot.get("githubAccountName");
            predicates.add(cb.like(githubPath, contactObject.getGithub()));
        }
        if (contactObject.getName() != null) {
            Path<String> namePath = contactEntityRoot.get("fullName");
            predicates.add(cb.like(namePath, contactObject.getName()));
        }
        if (contactObject.getPhoneNumber() != null) {
            Path<String> numberPath = contactEntityRoot.get("phoneNumber");
            predicates.add(cb.like(numberPath, contactObject.getPhoneNumber()));
        }
        if (contactObject.getOrganization() != null) {
            Path<String> organizationPath = contactEntityRoot.get("organization");
            predicates.add(cb.like(organizationPath, contactObject.getOrganization()));
        }
        query.select(contactEntityRoot)
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .orderBy(cb.asc(contactEntityRoot.get("fullName")));

        return entityManager.createQuery(query)
                .getResultList();
    }

}
