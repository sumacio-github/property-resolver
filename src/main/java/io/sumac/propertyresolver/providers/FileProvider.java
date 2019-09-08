package io.sumac.propertyresolver.providers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import io.sumac.propertyresolver.PropertyResolverException;

public class FileProvider extends RefreshableProvider implements Serializable {

	private static final long serialVersionUID = -9050367784611693236L;
	private final String filePath;

	public FileProvider(Path filePath) {
		this.filePath = filePath.toString();
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		var props = new Properties();
		try (final var inputStream = Files.newInputStream(Paths.get(filePath))) {
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw PropertyResolverException.errorReadingPropertyFile(filePath, e);
		}
	}

}
