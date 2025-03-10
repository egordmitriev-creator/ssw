package com.example.sorter;

import java.util.List;
import java.util.ArrayList;

public class CommandLineArgs {
    private String outputPath = ".";
    private String prefix = "";
    private boolean appendMode = false;
    private boolean shortStats = false;
    private boolean fullStats = false;
    private final List<String> inputFiles = new ArrayList<>();

    private ErrorHandler e = new ErrorHandler();

    public CommandLineArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 >= args.length) {
                        e.log("Missing value for output path");
                        return;
                    }
                    outputPath = args[++i];
                    break;
                case "-p":
                    if (i + 1 >= args.length) {
                        e.log("Missing value for prefix");
                        return;
                    }
                    prefix = args[++i];
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    if (args[i].startsWith("-")) {
                        e.log("Unknown flag: " + args[i]);
                    } else {
                        inputFiles.add(args[i]);
                    }
            }
        }
    }

    public String getOutputPath() { return outputPath; }
    public String getPrefix() { return prefix; }
    public boolean isAppendMode() { return appendMode; }
    public boolean isShortStats() { return shortStats; }
    public boolean isFullStats() { return fullStats; }
    public List<String> getInputFiles() { return inputFiles; }
}
