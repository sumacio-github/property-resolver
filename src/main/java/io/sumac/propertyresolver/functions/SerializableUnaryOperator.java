package io.sumac.propertyresolver.functions;

import java.io.Serializable;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface SerializableUnaryOperator extends UnaryOperator<String>, Serializable {

}
