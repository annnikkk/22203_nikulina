package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Dup extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner){
        emptyCheck(stack);
        stack.push(stack.peek());
    }
}
