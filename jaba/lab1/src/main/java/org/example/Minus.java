package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Minus extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner){
        emptyCheck(stack);
        int previous_top = stack.pop();
        emptyCheck(stack);
        int res = stack.pop() - previous_top;
        stack.push(res);
    }
}
