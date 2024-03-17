package org.example;

import java.util.Stack;
import java.util.Scanner;

public abstract class Operations {
    public abstract void Operation(Stack<Integer> stack, Scanner scanner);

    public static void emptyCheck(Stack<Integer> stack) {
        if (stack.empty()) {
            throw new RuntimeException("stack is empty");
        }
    }
}
