package com.ipaas.monitoringplstformsys.common.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;

@Service
public class I18nService {
    private final MessageSource messageSource;

    public I18nService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String msgKey, Object[] args) {
        try {
            return this.messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
        } catch (Exception var4) {
            return MessageFormat.format(msgKey, args);
        }
    }

    public String getMessage(String msgKey) {
        Locale locale = LocaleContextHolder.getLocale();

        try {
            return this.messageSource.getMessage(msgKey, (Object[])null, locale);
        } catch (Exception var4) {
            return msgKey;
        }
    }
}
