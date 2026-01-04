package com.ipaas.monitoringplstformsys.config;

public class LanguageCodeHolder {
    private static final ThreadLocal<String> languageCodeThreadLocal = new ThreadLocal<>();

    public static String getLanguageCode() {
        return languageCodeThreadLocal.get();
    }

    public static void setLanguageCode(String languageCode) {
        languageCodeThreadLocal.set(languageCode);
    }

    public static void clear() {
        languageCodeThreadLocal.remove();
    }
}
