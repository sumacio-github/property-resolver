package io.sumac.propertyresolver.utility;

public enum SupportedExtensions {
    XML, YAML, JSON, PROPERTIES;

    public static SupportedExtensions getExtension(String fileName) {
        if (!fileName.contains(".")) {
            return PROPERTIES;
        }
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        if (extension.equals("XML")) {
            return XML;
        } else if (extension.equals("JSON")) {
            return JSON;
        } else if (extension.equals("YML") || extension.equals("YAML")) {
            return YAML;
        } else {
            return PROPERTIES;
        }
    }
}
