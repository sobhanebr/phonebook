package org.example.phonebook.configuration;

import lombok.extern.slf4j.Slf4j;
import org.example.phonebook.util.exceptions.GenericApplicationException;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static org.example.phonebook.util.ValidatorUtil.isEmptyString;

/**
 * <p dir="rtl">
 * با توجه به اطلاعات تنظیم شده در فایل application.properties اتصال به واسط gitHub را برقرار ساخته و
 * دسترسی  به موجودیت
 * {@link GitHub}
 * در بخش‌های پروژه را ممکن می‌سازد
 * </p>
 */
@Slf4j
@Configuration
public class GithubConfiguration {
    private final String username;
    private final String password;
    private final String ghPersonalToken;
    private final String ghOrganization;
    private final String ghJwtToken;
    private final String ghAppInstallationToken;

    /**
     * <p dir="rtl">
     * مقادیر تنظیم شده در application.properties را بارگذاری می‌کند
     * </p>
     */
    public GithubConfiguration(@Value("${github.user.username}") String username,
                               @Value("${github.user.password}") String password,
                               @Value("${github.personal.token}") String ghPersonalToken,
                               @Value("${github.personal.organization}") String ghOrganization,
                               @Value("${github.jwt.token}") String ghJwtToken,
                               @Value("${github.app.installation.token}") String ghAppInstallationToken) {
        this.username = username;
        this.password = password;
        this.ghPersonalToken = ghPersonalToken;
        this.ghOrganization = ghOrganization;
        this.ghJwtToken = ghJwtToken;
        this.ghAppInstallationToken = ghAppInstallationToken;
    }

    /**
     * <p dir="rtl">
     * موجودیت
     * {@link GitHub}
     * را با توجه به مقادیر تنظیم شده ایجاد کرده برمی‌گرداند
     * </p>
     */
    @Bean
    public GitHub gitHubBean() {
        try {
            if (!isEmptyString(this.username) && !isEmptyString(this.password)) {
                return new GitHubBuilder().withPassword(this.username, this.password).build();
            } else if (!isEmptyString(this.ghPersonalToken)) {
                if (!isEmptyString(this.ghOrganization)) {
                    return new GitHubBuilder().withOAuthToken(this.ghPersonalToken, this.ghOrganization).build();
                } else {
                    return new GitHubBuilder().withOAuthToken(this.ghPersonalToken).build();
                }
            } else if (!isEmptyString(this.ghJwtToken)) {
                return new GitHubBuilder().withJwtToken(this.ghJwtToken).build();
            } else if (!isEmptyString(this.ghAppInstallationToken)) {
                return new GitHubBuilder().withAppInstallationToken(this.ghAppInstallationToken).build();
            } else {
                throw new GenericApplicationException("No github connection info was found");
            }
        } catch (IOException e) {
            log.error("Error while connecting to GitHub", e);
            return null;
        }
    }
}
