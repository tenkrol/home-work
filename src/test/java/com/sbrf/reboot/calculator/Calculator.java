package com.sbrf.reboot.calculator;

public class Calculator {

    public static int getAddition(int a, int b) {
        return a + b;
    }

    public static int getSubtraction(int a, int b) {
        return a - b;
    }

    public static int getMultiplication(int a, int b) {
        return a * b;
    }

    public static int getDivision(int a, int b) {
        return a / b;
    }

    public static int getRemainderOfDivision(int a, int b) {
        return a % b;
    }

    public static double getPow(double a, double b) {
        return Math.pow(a, b);
    }

    public static double getSqrt(double a) {
        return Math.sqrt(a);
    }

}