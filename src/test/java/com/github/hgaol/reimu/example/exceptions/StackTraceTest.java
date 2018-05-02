package com.github.hgaol.reimu.example.exceptions;

public class StackTraceTest {

    public static void main(String[] args) {
        foo();
    }

    private static void foo() {
        bar();
    }

    private static void bar() {
        throw new RuntimeException("OH!");
    }

}
