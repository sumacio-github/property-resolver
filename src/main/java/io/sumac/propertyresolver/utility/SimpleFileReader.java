package io.sumac.propertyresolver.utility;

import io.sumac.propertyresolver.annotations.NotNull;

import java.io.*;
import java.net.URL;

public class SimpleFileReader {
    public static String readFromRemoteFile(@NotNull String fileUrl) throws IOException {
        PreCondition.Parameter.notNull(fileUrl);
        URL url = new URL(fileUrl);
        try (InputStream inputStream = url.openStream()) {
            return readFromInputStream(inputStream);
        }
    }

    public static String readFromClasspath(@NotNull String resourceName) throws IOException {
        PreCondition.Parameter.notNull(resourceName);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream(resourceName)) {
            return readFromInputStream(inputStream);
        }
    }

    public static String readFromFile(@NotNull String filePath) throws IOException {
        PreCondition.Parameter.notNull(filePath);
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file)) {
            return readFromInputStream(inputStream);
        }
    }

    /**
     * Reads a file from the input byte stream. The stream remains open after this method returns.
     * @param inputStream the input stream.
     * @return the content read from the stream as a string.
     * @throws IOException if reading from the input stream results in an {@code IOException}
     */
    public static String readFromInputStream(@NotNull InputStream inputStream) throws IOException {
        PreCondition.Parameter.notNull(inputStream);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            boolean firstLine = true;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}