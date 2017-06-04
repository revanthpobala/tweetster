package com.revanth.twitter.thousandeyes.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Revanth on 5/30/2017.
 */

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.revanth.twitter"})
@EnableJpaRepositories(basePackages = {"com.revanth.twitter.thousandeyes.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}