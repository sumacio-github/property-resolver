package io.sumac.propertyresolver.sample;

import io.sumac.propertyresolver.annotations.Property;

public class Model8 {
	private String foundString;
	private String notFoundString;

	public String getFoundString() {
		return foundString;
	}

	@Property(name = "test.found.string", optional = true)
	public void setFoundString(String foundString) {
		this.foundString = foundString;
	}

	public String getNotFoundString() {
		return notFoundString;
	}

	@Property(name = "test.not_found.string", optional = true)
	public void setNotFoundString(String notFoundString) {
		this.notFoundString = notFoundString;
	}

}
