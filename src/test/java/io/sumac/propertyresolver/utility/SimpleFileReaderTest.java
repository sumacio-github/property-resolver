package io.sumac.propertyresolver.utility;

import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

public class SimpleFileReaderTest {
    private static final String FILE_TEXT = "hello\ngoodbye";

    @Test
    public void testReadFromClasspath() throws IOException {
        assertThat(SimpleFileReader.readFromClasspath("file.txt"), is(FILE_TEXT));
    }

    @Test
    public void testReadFromFile() throws IOException, URISyntaxException {
        assertThat(SimpleFileReader.readFromFile(Paths.get(getClass().getClassLoader().getResource("file.txt").toURI()).toFile().getAbsolutePath()), is(FILE_TEXT));
    }

    @Test
    public void readFromRemoteFile() throws IOException {
        assertThat(SimpleFileReader.readFromRemoteFile("https://raw.githubusercontent.com/sumacio-github/property-resolver/master/src/test/resources/file.txt"), is(FILE_TEXT));
    }
}
