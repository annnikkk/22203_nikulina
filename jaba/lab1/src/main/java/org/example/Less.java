package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Less extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner) {
        emptyCheck(stack);
        int previous_top = stack.pop();
        emptyCheck(stack);
        int res = 0;
        if(stack.pop() < previous_top) res = 1;
        stack.push(res);
    }
}
