package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Swap extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner) {
        emptyCheck(stack);
        int previous_top = stack.pop();
        emptyCheck(stack);
        int tmp = stack.pop();
        stack.push(previous_top);
        stack.push(tmp);
    }
}
