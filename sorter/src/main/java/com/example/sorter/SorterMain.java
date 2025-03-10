package com.example.sorter;

public class SorterMain {
    public static void main(String[] args) {
        try {
            CommandLineArgs cliArgs = new CommandLineArgs(args);

            DataProcessor processor = new DataProcessor(cliArgs);

            processor.process();

            Statistics stats = processor.getStatistics();
            if (cliArgs.isFullStats()) {
                stats.printFullStatistics();
            } else if (cliArgs.isShortStats()) {
                stats.printShortStatistics();
            }
        } catch (Exception e) {
            ErrorHandler.log("Error: " + e.getMessage());
        }
    }
}