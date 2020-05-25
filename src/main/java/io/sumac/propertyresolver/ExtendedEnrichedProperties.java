package io.sumac.propertyresolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.sumac.propertyresolver.utility.SimpleTextFileReader;
import io.sumac.propertyresolver.utility.SupportedExtensions;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Extension of {@code java.util.Properties} that adds some additional
 * support methods for loading configuration files of common types like json, xml, and yaml.
 *
 * @author ross
 */
public class ExtendedEnrichedProperties extends EnrichedProperties {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final YAMLMapper YAML_MAPPER = new YAMLMapper();
    private static final JavaPropsMapper PROPS_MAPPER = new JavaPropsMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    public ExtendedEnrichedProperties() {
        super();
    }

    public ExtendedEnrichedProperties(Properties properties) {
        super();
        putAll(properties);
    }

    public void loadFromJsonNode(JsonNode input) throws IOException {
        loadFromSource(() -> PROPS_MAPPER.writeValueAsProperties(input));
    }

    public void loadFromJsonString(String input) throws IOException {
        loadFromJsonNode(JSON_MAPPER.readTree(input));
    }

    public void loadFromYamlString(String input) throws IOException {
        loadFromJsonNode(YAML_MAPPER.readTree(input));
    }

    public void loadFromObject(Object input) throws IOException {
        loadFromJsonNode(JSON_MAPPER.valueToTree(input));
    }

    public void loadFromXmlString(String input, Class<?> schema) throws IOException {
        loadFromObject(XML_MAPPER.readValue(input, schema));
    }

    public void loadFromPropertiesString(String input) throws IOException {
        load(new StringReader(input));
    }

    public void loadFromSystemArgs() {
        putAll(System.getProperties());
    }

    public void loadFromEnv() {
        this.putAll(System.getenv());
    }

    public void loadFromFile(String filePath) throws IOException {
        loadFromSupportedFile(SimpleTextFileReader.readFromFile(filePath), filePath);
    }

    public void loadFromClasspath(String classpathLocation) throws IOException {
        loadFromSupportedFile(SimpleTextFileReader.readFromClasspath(classpathLocation), classpathLocation);
    }

    public void loadFromURL(String fileUrl) throws IOException {
        loadFromSupportedFile(SimpleTextFileReader.readFromRemoteFile(fileUrl), fileUrl);
    }

    public void loadXmlFromFile(String filePath, Class<?> schema) throws IOException {
        loadFromXmlString(SimpleTextFileReader.readFromFile(filePath), schema);
    }

    public void loadXmlFromClasspath(String classpathLocation, Class<?> schema) throws IOException {
        loadFromXmlString(SimpleTextFileReader.readFromClasspath(classpathLocation), schema);
    }

    public void loadXmlFromURL(String fileUrl, Class<?> schema) throws IOException {
        loadFromXmlString(SimpleTextFileReader.readFromRemoteFile(fileUrl), schema);
    }

    private void loadFromSupportedFile(String textContent, String path) throws IOException {
        switch (SupportedExtensions.getExtension(path)) {
            case YAML:
                loadFromYamlString(textContent);
                break;
            case JSON:
                loadFromJsonString(textContent);
                break;
            case XML:
                throw new IllegalArgumentException("XML is not supported by this method. Instead use one of the 'loadXmlFromXXX()' methods.");
            default:
                loadFromPropertiesString(textContent);
        }
    }

    public static ExtendedEnrichedProperties toEnriched(Properties props) {
        return new ExtendedEnrichedProperties(props);
    }

    public EnrichedProperties filterByRegex(String regex) {
       return toEnriched(super.filterByRegex(regex));
    }

    public EnrichedProperties filterByStartsWith(String keyStartsWith) {
        return toEnriched(super.filterByStartsWith(keyStartsWith));
    }

    public ExtendedEnrichedProperties getChildProperties(String parentPropertyKey) {
        return toEnriched(super.getChildProperties(parentPropertyKey));
    }
}
