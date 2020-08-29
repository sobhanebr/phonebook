package org.example.phonebook.model.bl;

import javafx.util.Pair;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.phonebook.dao.ContactRepository;
import org.example.phonebook.dao.GitHubRepos;
import org.example.phonebook.model.to.ContactEntity;
import org.example.phonebook.model.to.GithubRepositoryEntity;
import org.example.phonebook.util.exceptions.GenericApplicationException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <p dir="rtl">
 * سرویس ارتباط با GitHub API را فراهم می‌آورد
 * </p>
 */
@Slf4j
@Service
public class GithubApiService {

    private final GitHub gitHub;
    private final ExecutorService gitHubServiceExecutor;
    private final GitHubRepos gitHubRepos;
    private final ContactRepository contactRepository;

    @Autowired
    public GithubApiService(GitHub gitHub, GitHubRepos gitHubRepos, ContactRepository contactRepository) {
        this.gitHub = gitHub;
        this.gitHubRepos = gitHubRepos;
        this.contactRepository = contactRepository;
        gitHubServiceExecutor = Executors.newFixedThreadPool(3);
    }

    /**
     * <p dir="rtl">
     * از روی نام repositoryها، شیء متناسب را ایجاد کرده برای مخاطب تنظیم می‌کند
     * </p>
     *
     * @param contactEntity مخاطب مدنظر
     * @param repoNames     نام repositoryها
     */
    public void setContactRepository(ContactEntity contactEntity, @NonNull List<String> repoNames) {
        List<GithubRepositoryEntity> repositoryEntities = new ArrayList<>(repoNames.size());
        for (String repoName : repoNames) {
            GithubRepositoryEntity repositoryEntity = new GithubRepositoryEntity();
            repositoryEntity.setName(repoName);
            repositoryEntity.setContactId(contactEntity.getId());
            repositoryEntity.setOwner(contactEntity);
            repositoryEntities.add(repositoryEntity);
        }
        gitHubRepos.saveAll(repositoryEntities);
        log.info("Saved '{}' repos successfully : [{}]", contactEntity.getGithubAccountName(), repoNames.toString());
        contactEntity.setRepositories(repositoryEntities);
    }


    /**
     * Takes result of done futures.
     *
     * @param future list of futures.
     */
    private Pair<ContactEntity, List<String>> takeResults(Future<Pair<ContactEntity, List<String>>> future) {
        try {
            if (!future.isCancelled())
                return future.get();
        } catch (Exception e) {
            log.error("Error while executing task. ", e);
        }
        return null;
    }

    /**
     * <p dir="rtl">
     * عملیات کشف repositoryهای مخاطب ورودی را ایجاد کرده برمی‌گرداند
     * </p>
     *
     * @param contactEntity مخاطب ورودی
     * @return {@link Callable<Pair>}
     */
    public Callable<Pair<ContactEntity, List<String>>> reposNameDiscoveryCallable(final ContactEntity contactEntity) {
        if (gitHub == null) throw new GenericApplicationException("No github connection provided");
        return () -> {
            try {
                GHUser ghUser = gitHub.getUser(contactEntity.getGithubAccountName());
                return new Pair<>(contactEntity, ghUser.listRepositories().toList()
                        .parallelStream()
                        .map(GHRepository::getName)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                log.error("Error while loading user['{}'] repos", contactEntity, e);
                return null;
            }
        };
    }

    /**
     * @return availability در صورت برقراری اتصال برخط به GitHub API و خالی‌بودن صف عملیات‌های در حال انجام true و در غیر اینصورت false
     */
    public boolean isAvailable() {
        return gitHub != null &&
                !gitHub.isOffline() &&
                gitHub.isCredentialValid() &&
                ((ThreadPoolExecutor) gitHubServiceExecutor).getQueue().isEmpty();
    }

    /**
     * <p dir="rtl">
     * برای مخاطبینی که repositoryهای‌شان کشف نشده، عملیات کشف ایجاد کرده و به
     * {@code gitHubServiceExecutor}
     * اضافه می‌کند
     * در نهایت در صورت موفقیت در اکتشاف repositoryها، آن را برای مخاطب تنظیم کرده
     * مخاظب را از لیست مخاطبین کشف‌ نشده حذف می‌کند
     * </p>
     *
     * @param undiscoveredRepositoryAccounts مخاطبینی که repositoryهای‌شان کشف نشده
     */
    public synchronized void setContactsRepository(List<ContactEntity> undiscoveredRepositoryAccounts) {
        List<Future<Pair<ContactEntity, List<String>>>> futureList = undiscoveredRepositoryAccounts.stream()
                .map(contactEntity -> gitHubServiceExecutor.submit(reposNameDiscoveryCallable(contactEntity)))
                .collect(Collectors.toList());
        futureList.forEach(pairFuture -> {
            final Pair<ContactEntity, List<String>> results = takeResults(pairFuture);
            if (results != null && results.getValue() != null) {
                ContactEntity contactEntity = results.getKey();
                setContactRepository(contactEntity, results.getValue());
                undiscoveredRepositoryAccounts.remove(contactEntity);
                contactRepository.save(contactEntity);
            }
        });
    }
}
