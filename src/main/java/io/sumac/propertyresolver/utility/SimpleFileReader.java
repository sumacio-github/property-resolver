package io.sumac.propertyresolver.utility;

import java.io.*;
import java.net.URL;

public class SimpleFileReader {
    public static String readFromRemoteFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream inputStream = url.openStream()) {
            return readFromInputStream(inputStream);
        }
    }

    public static String readFromClasspath(String resourceName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream(resourceName)) {
            return readFromInputStream(inputStream);
        }
    }

    public static String readFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file)) {
            return readFromInputStream(inputStream);
        }
    }

    public static String readFromInputStream(InputStream inputStream) throws IOException {
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
