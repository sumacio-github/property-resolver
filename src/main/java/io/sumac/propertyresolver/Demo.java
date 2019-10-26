package io.sumac.propertyresolver;

import java.nio.file.Paths;

public class Demo {

	public static void main(String[] args) {
		var properties = PropertyResolver.register().fromClassPath("config1.properties")
				.fromFile(Paths.get("/home/ross/git/sumac.io/property-resolver/src/main/resources/config3.properties"))
				.fromClassPath("config2.properties").property("test.6", "test_six").debug(true).build();
		System.out.println(properties);
		System.out.println(properties.getInt("test.5").get());
	}

}
