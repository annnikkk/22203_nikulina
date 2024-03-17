package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Point extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner) {
        emptyCheck(stack);
        System.out.println(stack.pop());
    }
}
