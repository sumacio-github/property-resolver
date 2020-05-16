package io.sumac.propertyresolver.utility;

import java.io.IOException;

public interface IOThrowingSupplier<T> {
    T get() throws IOException;
}
