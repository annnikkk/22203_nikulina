package org.example;

import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;

public class Over extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s) {
        emptyCheck(stack);
        int previous_top = stack.pop();
        emptyCheck(stack);
        int tmp = stack.peek();
        stack.push(previous_top);
        stack.push(tmp);
    }
}
