package io.sumac.propertyutils.utility;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

public class SimpleTextFileReaderTest {
    private static final String FILE_TEXT = "hello\ngoodbye";

    @Test
    public void testReadFromClasspath() throws IOException {
        assertThat(SimpleTextFileReader.readFromClasspath("file.txt"), is(FILE_TEXT));
    }

    @Test
    public void testReadFromFile() throws IOException, URISyntaxException {
        assertThat(SimpleTextFileReader.readFromFile(Paths.get(getClass().getClassLoader().getResource("file.txt").toURI()).toFile().getAbsolutePath()), is(FILE_TEXT));
    }

    @Test
    public void readFromRemoteFile() throws IOException {
        assertThat(SimpleTextFileReader.readFromRemoteFile("https://raw.githubusercontent.com/sumacio-github/property-resolver/master/property-resolver/src/test/resources/file.txt"), is(FILE_TEXT));
    }
}
