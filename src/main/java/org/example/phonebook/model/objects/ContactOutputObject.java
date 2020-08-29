package org.example.phonebook.model.objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.phonebook.model.to.ContactEntity;
import org.example.phonebook.model.to.GithubRepositoryEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p dir="rtl">
 * شیء خروجی مخاطبین را ارائه می‌دهد
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactOutputObject extends ContactObject {
    private List<String> repos;

    public static ContactOutputObject ofContactEntity(ContactEntity contactEntity) {
        ContactOutputObject contactObject = new ContactOutputObject();
        contactObject.setName(contactEntity.getFullName());
        contactObject.setEmail(contactEntity.getEmailAddress());
        contactObject.setGithub(contactEntity.getGithubAccountName());
        contactObject.setOrganization(contactEntity.getOrganization());
        contactObject.setPhoneNumber(contactEntity.getPhoneNumber());
        contactObject.setRepos(contactEntity.getRepositories().parallelStream()
                .map(GithubRepositoryEntity::getName)
                .collect(Collectors.toList()));
        return contactObject;
    }

}
