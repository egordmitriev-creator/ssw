package com.example.sorter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CommandLineArgsTest {

    @Test
    void testDefaultParam() {
        String[] args = {"input.txt"};
        CommandLineArgs clientArgs = new CommandLineArgs(args);

        assertEquals(".", clientArgs.getOutputPath());
        assertEquals("", clientArgs.getPrefix());

        assertEquals(1, clientArgs.getInputFiles().size());
        assertTrue(clientArgs.getInputFiles().contains("input.txt"));

        assertFalse(clientArgs.isAppendMode());
        assertFalse(clientArgs.isShortStats());
        assertFalse(clientArgs.isFullStats());
    }

    @Test
    void testParsArgs() {
        String[] args = {"-o", "/output/path", "-f", "-p", "somePref_", "input1.txt", "input2.txt"};
        CommandLineArgs clientArgs = new CommandLineArgs(args);

        assertEquals("/output/path", clientArgs.getOutputPath());
        assertEquals("somePref_", clientArgs.getPrefix());
        assertEquals(2, clientArgs.getInputFiles().size());

        assertTrue(clientArgs.getInputFiles().contains("input1.txt"));
        assertTrue(clientArgs.getInputFiles().contains("input2.txt"));
        assertTrue(clientArgs.isFullStats());

        assertFalse(clientArgs.isShortStats());
        assertFalse(clientArgs.isAppendMode());
    }
}