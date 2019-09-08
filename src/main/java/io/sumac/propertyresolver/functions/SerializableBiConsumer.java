package io.sumac.propertyresolver.functions;

import java.io.Serializable;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface SerializableBiConsumer extends BiConsumer<String, String>, Serializable {

}
