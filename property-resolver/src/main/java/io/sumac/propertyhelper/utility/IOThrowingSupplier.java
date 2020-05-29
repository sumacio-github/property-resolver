package io.sumac.propertyhelper.utility;

import java.io.IOException;

public interface IOThrowingSupplier<T> {
    T get() throws IOException;
}
