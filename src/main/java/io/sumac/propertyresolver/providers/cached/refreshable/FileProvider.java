package io.sumac.propertyresolver.providers.cached.refreshable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import io.sumac.propertyresolver.PropertyResolverException;

public class FileProvider extends RefreshableProvider {

	private final String filePath;

	public FileProvider(Path filePath) {
		this.filePath = filePath.toString();
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		Properties props = new Properties();
		try (final InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw PropertyResolverException.errorReadingPropertyFile(filePath, e);
		}
	}

}
