package io.ylab.loggableaspectstarter;

import io.ylab.loggableaspectstarter.aop.aspect.LoggableAspect;
import io.ylab.loggableaspectstarter.configuration.LoggableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LoggableAspect.class, LoggableAutoConfiguration.class})
public class MyLoggerStarter {
}
