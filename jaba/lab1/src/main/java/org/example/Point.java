package org.example;

import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;

public class Point extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s) {
        emptyCheck(stack);
        System.out.println(stack.pop());
    }
}
