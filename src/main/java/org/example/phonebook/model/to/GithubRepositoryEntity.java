package org.example.phonebook.model.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p dir="rtl">
 * موجودیت repository مخاطبین را ارائه می‌دهد
 * </p>
 */
@Data
@Entity
@Table(name = "repos", schema = "public")
public class GithubRepositoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_id", nullable = false, updatable = false)
    private Integer contactId;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private ContactEntity owner;

}
