package io.github.housingfinancial.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceConfig {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames("locale/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor getMessageSourceAccessor(){
        return new MessageSourceAccessor(messageSource());
    }

}
