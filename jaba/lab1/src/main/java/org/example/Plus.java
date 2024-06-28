package org.example;

import java.io.OutputStream;
import java.util.Stack;
import java.util.Scanner;

public class Plus extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s){
        emptyCheck(stack);
        int previous_top = stack.pop();
        emptyCheck(stack);
        int res = previous_top + stack.pop();
        stack.push(res);
    }
}
