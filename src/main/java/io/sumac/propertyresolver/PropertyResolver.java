package io.sumac.propertyresolver;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyResolver {
	private final List<EnhancedProperties> propertiesBuffer = new ArrayList<>();
	private boolean debug = false;
	private boolean reversePriority = false;

	private PropertyResolver() {

	}

	public static PropertyResolver register() {
		return new PropertyResolver();
	}

	public PropertyResolver fromFile(Path filePath) {
		propertiesBuffer.add(PropertyResolverUtils.toProperties(filePath));
		return this;
	}

	public PropertyResolver fromClassPath(String fileName) {
		propertiesBuffer.add(PropertyResolverUtils.toProperties(fileName));
		return this;
	}

	public PropertyResolver property(String key, String value) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return properties(map);
	}

	public PropertyResolver properties(Map<String, String> properties) {
		propertiesBuffer.add(PropertyResolverUtils.toProperties(properties));
		return this;
	}

	public PropertyResolver reversePriority() {
		this.reversePriority = !this.reversePriority;
		return this;
	}

	public PropertyResolver debug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public EnhancedProperties build() {
		List<EnhancedProperties> localProperties = getOrderedByPriority();
		EnhancedProperties output = new EnhancedProperties();
		localProperties.forEach(output::putAll);
		if (debug) {
			show(output);
		}
		return output;
	}

	private List<EnhancedProperties> getOrderedByPriority() {
		List<EnhancedProperties> copy = new ArrayList<>(propertiesBuffer);
		if (reversePriority) {
			Collections.reverse(copy);
		}
		return copy;
	}

	private void show(EnhancedProperties properties) {
		List<String> keys = properties.keySet().stream().map(Object::toString).collect(Collectors.toList());
		Collections.sort(keys);
		StringBuilder builder = new StringBuilder();
		keys.forEach(key -> {
			if (properties.getString(key).isPresent()) {
				builder.append(key).append(" = ").append(properties.getString(key).get());
			} else {
				builder.append(key).append(" = NULL");
			}
			builder.append("\n");
		});
		System.out.println(builder.toString());
	}
}
