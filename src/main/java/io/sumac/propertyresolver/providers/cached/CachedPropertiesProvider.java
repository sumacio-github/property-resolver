package io.sumac.propertyresolver.providers.cached;

import java.util.Set;

import io.sumac.propertyresolver.providers.PropertiesProvider;

public abstract class CachedPropertiesProvider extends PropertiesProvider {

	public abstract Set<String> getPropertyNames();

}
