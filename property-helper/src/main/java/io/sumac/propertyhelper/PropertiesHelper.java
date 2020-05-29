package io.sumac.propertyhelper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.sumac.propertyhelper.utility.SupportedExtensions;
import io.sumac.propertyhelper.utility.SimpleTextFileReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

/**
 * Extension of {@code java.util.Properties} that adds some additional
 * support methods for loading configuration files of common types like json, xml, and yaml.
 *
 * @author ross
 */
public class PropertiesHelper {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final YAMLMapper YAML_MAPPER = new YAMLMapper();
    private static final JavaPropsMapper PROPS_MAPPER = new JavaPropsMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    public static void loadFromJsonNode(java.util.Properties properties, JsonNode input) throws IOException {
        properties.putAll(PROPS_MAPPER.writeValueAsProperties(input));
    }

    public static void loadFromJsonString(java.util.Properties properties, String input) throws IOException {
        loadFromJsonNode(properties, JSON_MAPPER.readTree(input));
    }

    public static void loadFromYamlString(java.util.Properties properties, String input) throws IOException {
        loadFromJsonNode(properties, YAML_MAPPER.readTree(input));
    }

    public static void loadFromObject(java.util.Properties properties, Object input) throws IOException {
        loadFromJsonNode(properties, JSON_MAPPER.valueToTree(input));
    }

    public static void loadFromXmlString(java.util.Properties properties, String input, Class<?> schema) throws IOException {
        loadFromObject(properties, XML_MAPPER.readValue(input, schema));
    }

    public static void loadFromPropertiesString(java.util.Properties properties, String input) throws IOException {
        properties.load(new StringReader(input));
    }

    public static void loadFromSystemArgs(java.util.Properties properties) {
        properties.putAll(System.getProperties());
    }

    public static void loadFromEnv(java.util.Properties properties) {
        properties.putAll(System.getenv());
    }

    public static void loadFromFile(java.util.Properties properties, String filePath) throws IOException {
        loadFromSupportedFile(properties, SimpleTextFileReader.readFromFile(filePath), filePath);
    }

    public static void loadFromClasspath(java.util.Properties properties, String classpathLocation) throws IOException {
        loadFromSupportedFile(properties, SimpleTextFileReader.readFromClasspath(classpathLocation), classpathLocation);
    }

    public static void loadFromURL(java.util.Properties properties, String fileUrl) throws IOException {
        loadFromSupportedFile(properties, SimpleTextFileReader.readFromRemoteFile(fileUrl), fileUrl);
    }

    public static void loadXmlFromFile(java.util.Properties properties, String filePath, Class<?> schema) throws IOException {
        loadFromXmlString(properties, SimpleTextFileReader.readFromFile(filePath), schema);
    }

    public static void loadXmlFromClasspath(java.util.Properties properties, String classpathLocation, Class<?> schema) throws IOException {
        loadFromXmlString(properties, SimpleTextFileReader.readFromClasspath(classpathLocation), schema);
    }

    public static void loadXmlFromURL(java.util.Properties properties, String fileUrl, Class<?> schema) throws IOException {
        loadFromXmlString(properties, SimpleTextFileReader.readFromRemoteFile(fileUrl), schema);
    }

    private static void loadFromSupportedFile(java.util.Properties properties, String textContent, String path) throws IOException {
        switch (SupportedExtensions.getExtension(path)) {
            case YAML:
                loadFromYamlString(properties, textContent);
                break;
            case JSON:
                loadFromJsonString(properties, textContent);
                break;
            case XML:
                throw new IllegalArgumentException("XML is not supported by this method. Instead use one of the 'loadXmlFromXXX()' methods.");
            default:
                loadFromPropertiesString(properties, textContent);
        }
    }

    public static <T> T bind(java.util.Properties properties, Class<T> type) throws IOException {
        Properties props = new Properties();
        properties.forEach((k,v) -> {
            if (Objects.nonNull(properties.get(k))) {
                props.put(k, Properties.InterpolationHelper.interpolate(properties, properties.get(k).toString()));
            }
        });
        JsonParser parser = PROPS_MAPPER.getFactory().createParser(props);
        return PROPS_MAPPER.readValue(parser, type);
    }
}
