package ru.gb.aspect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(value = "starter-aspect.properties.enabled", havingValue = "true")
public class StarterAspectAutoConfiguration {

    @Bean
    public AnnotationsHandler annotationsHandler(LoggingProperties loggingProperties) {
        return new AnnotationsHandler(loggingProperties);
    }
}
