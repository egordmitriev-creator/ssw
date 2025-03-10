package com.example.sorter;

public class Statistics {
    private int intCount = 0;
    private int minInt = Integer.MAX_VALUE;
    private int maxInt = Integer.MIN_VALUE;
    private long intSum = 0;

    private int floatCount = 0;
    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;
    private double floatSum = 0;

    private int stringCount = 0;
    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = 0;

    public void updateIntegerStats(int value) {
        intCount++;
        minInt = Math.min(minInt, value);
        maxInt = Math.max(maxInt, value);
        intSum += value;
    }

    public void updateFloatStats(double value) {
        floatCount++;
        minFloat = Math.min(minFloat, value);
        maxFloat = Math.max(maxFloat, value);
        floatSum += value;
    }

    public void updateStringStats(String value) {
        stringCount++;
        minStringLength = Math.min(minStringLength, value.length());
        maxStringLength = Math.max(maxStringLength, value.length());
    }

    public void printShortStatistics() {
        System.out.println("Short statistic:");
        System.out.println("Integer count: " + intCount);
        System.out.println("Float count: " + floatCount);
        System.out.println("String count: " + stringCount);
    }

    public void printFullStatistics() {
        System.out.println("Full statistic:");
        if (intCount > 0) {
            System.out.println("Integer: count=" + intCount + ", min=" + minInt + ", max=" + maxInt + ", sum=" + intSum + ", avg=" + (intSum / intCount));
        }
        if (floatCount > 0) {
            System.out.println("Float: count=" + floatCount + ", min=" + minFloat + ", max=" + maxFloat + ", sum=" + floatSum + ", avg=" + (floatSum / floatCount));
        }
        if (stringCount > 0) {
            System.out.println("String: count=" + stringCount + ", minLength=" + minStringLength + ", maxLength=" + maxStringLength);
        }
    }

    public int getIntCount() { return intCount; }
    public int getMinInt() { return minInt; }
    public int getMaxInt() { return maxInt; }
    public long getIntSum() { return intSum; }

    public int getFloatCount() { return floatCount; }
    public double getMinFloat() { return minFloat; }
    public double getMaxFloat() { return maxFloat; }
    public double getFloatSum() { return floatSum; }

    public int getStringCount() { return stringCount; }
    public int getMinStringLength() { return minStringLength; }
    public int getMaxStringLength() { return maxStringLength; }
}