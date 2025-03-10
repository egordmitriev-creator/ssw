package com.example.sorter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataProcessor {
    private final CommandLineArgs cliArgs;
    private final Statistics stats = new Statistics();

    private boolean hasIntegers = false;
    private boolean hasFloats = false;
    private boolean hasStrings = false;

    public DataProcessor(CommandLineArgs cliArgs) {
        this.cliArgs = cliArgs;
    }

    public void process() throws IOException {
        Path outputDir = Paths.get(cliArgs.getOutputPath());
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        for (String inputFile : cliArgs.getInputFiles()) {
            processFile(inputFile);
        }

        createOutputFiles();
    }

    private void processFile(String inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DataType type = classifyLine(line);
                switch (type) {
                    case INTEGER:
                        hasIntegers = true;
                        stats.updateIntegerStats(Integer.parseInt(line));
                        break;
                    case FLOAT:
                        hasFloats = true;
                        stats.updateFloatStats(Double.parseDouble(line));
                        break;
                    case STRING:
                        hasStrings = true;
                        stats.updateStringStats(line);
                        break;
                }
            }
        } catch (IOException e) {
            ErrorHandler.log("Error reading file " + inputFile + ": " + e.getMessage());
        }
    }

    private void createOutputFiles() throws IOException {
        if (hasIntegers) {
            writeDataToFile("integers.txt", DataType.INTEGER);
        }
        if (hasFloats) {
            writeDataToFile("floats.txt", DataType.FLOAT);
        }
        if (hasStrings) {
            writeDataToFile("strings.txt", DataType.STRING);
        }
    }

    private void writeDataToFile(String fileName, DataType dataType) throws IOException {
        String fullPath = cliArgs.getOutputPath() + "/" + cliArgs.getPrefix() + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, cliArgs.isAppendMode()))) {
            for (String inputFile : cliArgs.getInputFiles()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (classifyLine(line) == dataType) {
                            writer.write(line + "\n");
                        }
                    }
                } catch (IOException e) {
                    ErrorHandler.log("Error reading file: " + inputFile + ": " + e.getMessage());
                }
            }
        }
    }

    public DataType classifyLine(String line) {
        if (line.matches("^-?\\d+$")) {
            return DataType.INTEGER;
        } else if (line.matches("^-?\\d+\\.\\d+")) {
            return DataType.FLOAT;
        } else {
            return DataType.STRING;
        }
    }

    public Statistics getStatistics() {
        return stats;
    }
}