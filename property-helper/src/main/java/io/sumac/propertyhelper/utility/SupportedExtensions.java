package io.sumac.propertyhelper.utility;

public enum SupportedExtensions {
    XML, YAML, JSON, PROPERTIES;

    public static SupportedExtensions getExtension(String fileName) {
        if (!fileName.contains(".")) {
            return PROPERTIES;
        }
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        if ("XML".equals(extension)) {
            return XML;
        } else if ("JSON".equals(extension)) {
            return JSON;
        } else if ("YML".equals(extension) || "YAML".equals(extension)) {
            return YAML;
        } else {
            return PROPERTIES;
        }
    }
}
