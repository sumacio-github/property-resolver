package io.sumac.propertyresolver.sample;

import io.sumac.propertyresolver.annotations.Property;

public class Model9 {

	private String foundString;

	private String notFoundString;

	public Model9(@Property(name = "test.found.string", optional = true) String foundString,
			@Property(name = "test.not_found.string", optional = true) String notFoundString) {
		super();
		this.foundString = foundString;
		this.notFoundString = notFoundString;
	}

	public String getFoundString() {
		return foundString;
	}

	public void setFoundString(String foundString) {
		this.foundString = foundString;
	}

	public String getNotFoundString() {
		return notFoundString;
	}

	public void setNotFoundString(String notFoundString) {
		this.notFoundString = notFoundString;
	}

}
