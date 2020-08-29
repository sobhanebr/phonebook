package org.example.phonebook.model.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.phonebook.model.objects.ContactObject;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

import static org.example.phonebook.util.ConverterUtil.emptyIfNull;

/**
 * <p dir="rtl">
 * موجودیت مخاطبین را ارائه می‌دهد
 * </p>
 */
@Data
@Entity
@Table(name = "contacts", schema = "public")
public class ContactEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Email
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "organization", nullable = false)
    private String organization;

    @Column(name = "github_account_name", nullable = false, unique = true)
    private String githubAccountName;

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    private List<GithubRepositoryEntity> repositories;

    public static ContactEntity ofAddContactInputObject(ContactObject inputObject) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setFullName(inputObject.getName());
        contactEntity.setEmailAddress(inputObject.getEmail());
        contactEntity.setGithubAccountName(inputObject.getGithub());
        contactEntity.setPhoneNumber(inputObject.getPhoneNumber());
        contactEntity.setOrganization(inputObject.getOrganization());
        return contactEntity;
    }

    public List<GithubRepositoryEntity> getRepositories() {
        return emptyIfNull(repositories);
    }
}
