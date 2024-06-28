package org.example;

import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;

public class Rot extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s) {
        emptyCheck(stack);
        int first = stack.pop();
        emptyCheck(stack);
        int second = stack.pop();
        emptyCheck(stack);
        int third = stack.pop();
        stack.push(first);
        stack.push(third);
        stack.push(second);
    }
}
