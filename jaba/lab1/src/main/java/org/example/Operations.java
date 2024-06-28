package org.example;

import java.io.OutputStream;
import java.util.Stack;
import java.util.Scanner;

public abstract class Operations {
    public abstract void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s);

    public static void emptyCheck(Stack<Integer> stack) {
        if (stack.empty()) {
            //log
            throw new RuntimeException("stack is empty");
        }
    }
}
