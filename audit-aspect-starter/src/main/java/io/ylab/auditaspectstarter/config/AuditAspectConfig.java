package io.ylab.auditaspectstarter.config;

import io.ylab.auditaspectstarter.aop.aspect.AuditAspect;
import io.ylab.auditaspectstarter.dao.ActionRepository;
import io.ylab.auditaspectstarter.dao.ActionRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuditAspectConfig {
    @Bean
    public ActionRepository actionRepository() {
        return new ActionRepositoryImpl();
    }

    @Bean
    public AuditAspect auditAspect(ActionRepository actionRepository) {
        return new AuditAspect(actionRepository);
    }
}
