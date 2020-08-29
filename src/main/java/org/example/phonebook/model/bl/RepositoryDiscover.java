package org.example.phonebook.model.bl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.phonebook.model.to.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p dir="rtl">
 * اکتشاف repository برای مخاطبین را انجام می‌دهد
 * به این صورت که هر ۱۰ ثانیه یک‌بار در صورت وجود مخاطبی در لیست مخاطبین کشف نشده
 * و برقراری اتصال به واسط گیت‌هاب، عملیات اکتشاف repository برای آنان را ایجاد و شروع می‌کند
 * </p>
 */
@Slf4j
@Component
public class RepositoryDiscover {

    private final GithubApiService githubApiService;
    private final List<ContactEntity> undiscoveredRepositoryAccounts;
    private final ScheduledFuture<?> scheduledFuture;

    @Autowired
    public RepositoryDiscover(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
        undiscoveredRepositoryAccounts = new ArrayList<>();
        ScheduledExecutorService gitHubServiceExecutor = Executors.newScheduledThreadPool(1);
        scheduledFuture = gitHubServiceExecutor.scheduleWithFixedDelay(runnableTask(),
                0, 10, TimeUnit.SECONDS);
    }

    /**
     * <p dir="rtl">
     * مخاطب مدنظر را به لیست مخاطبین کشف نشده می‌افزاید
     * </p>
     *
     * @param contactEntity مخاطب کشف نشده‌ی جدید
     */
    public void addUndiscoveredContact(ContactEntity contactEntity) {
        undiscoveredRepositoryAccounts.add(contactEntity);
        log.info("Contact[{}] was added to undiscovered contact list", contactEntity);
    }

    /**
     * <p dir="rtl">
     * عملیات اکتشاف repository برای لیست مخاطبین کشف نشده را برمی‌گرداند
     * </p>
     *
     * @return Runnable
     */
    @NonNull
    private Runnable runnableTask() {
        return () -> {
            if (scheduledFuture == null ||
                    scheduledFuture.isCancelled() ||
                    undiscoveredRepositoryAccounts.isEmpty() ||
                    !githubApiService.isAvailable()) return;
            githubApiService.setContactsRepository(undiscoveredRepositoryAccounts);
        };
    }


}
