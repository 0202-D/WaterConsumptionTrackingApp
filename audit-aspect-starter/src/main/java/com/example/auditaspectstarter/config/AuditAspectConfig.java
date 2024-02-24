package com.example.auditaspectstarter.config;

import com.example.auditaspectstarter.aop.aspect.AuditAspect;
import com.example.auditaspectstarter.dao.ActionRepository;
import com.example.auditaspectstarter.dao.JdbcActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuditAspectConfig {
    @Bean
    public ActionRepository actionRepository() {
        return new JdbcActionRepository();
    }

    @Bean
    public AuditAspect auditAspect(ActionRepository actionRepository) {
        return new AuditAspect(actionRepository);
    }
}
