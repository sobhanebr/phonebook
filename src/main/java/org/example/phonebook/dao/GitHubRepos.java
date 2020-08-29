package org.example.phonebook.dao;

import org.example.phonebook.model.to.GithubRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p dir="rtl">
 * ارتباط با پایگاه‌داده جهت امکان خوانش و نوشتار موجودیت
 * {@link GithubRepositoryEntity}
 * را فراهم می‌آورد
 * </p>
 */
public interface GitHubRepos extends JpaRepository<GithubRepositoryEntity, Integer> {
}