package com.example.sorter;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class DataProcessorTest {
    @Test
    void testClassifyLine() {
        DataProcessor processor = new DataProcessor(new CommandLineArgs(new String[]{}));

        assertEquals(DataType.INTEGER, processor.classifyLine("52"));
        assertEquals(DataType.FLOAT, processor.classifyLine("25.17"));
        assertEquals(DataType.STRING, processor.classifyLine("someString"));
    }

    @Test
    void testProcessFile() throws IOException {
        Path inputFile = Files.createTempFile("input", ".txt");
        Files.write(inputFile, "52\n25.17\nsomeString".getBytes());

        CommandLineArgs cliArgs = new CommandLineArgs(new String[]{inputFile.toString()});
        DataProcessor processor = new DataProcessor(cliArgs);
        processor.process();

        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        assertTrue(Files.exists(intFile));
        assertTrue(Files.exists(floatFile));
        assertTrue(Files.exists(stringFile));

        assertEquals("52", Files.readAllLines(intFile).get(0));
        assertEquals("25.17", Files.readAllLines(floatFile).get(0));
        assertEquals("someString", Files.readAllLines(stringFile).get(0));

        Files.deleteIfExists(intFile);
        Files.deleteIfExists(floatFile);
        Files.deleteIfExists(stringFile);
        Files.deleteIfExists(inputFile);
    }
}