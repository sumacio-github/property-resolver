package io.sumac.propertyresolver.functions;

import java.io.Serializable;
import java.util.function.Consumer;

@FunctionalInterface
public interface SerializableConsumer extends Consumer<String>, Serializable {

}
