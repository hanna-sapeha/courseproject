package com.hanna.sapeha.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean containerEntityManager = new LocalContainerEntityManagerFactoryBean();
        containerEntityManager.setDataSource(dataSource);
        containerEntityManager.setPackagesToScan("com.hanna.sapeha.app.repository.model");
        containerEntityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return containerEntityManager;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
