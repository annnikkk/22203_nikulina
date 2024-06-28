package org.example;

import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;

public class Drop extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s){
        emptyCheck(stack);
        stack.pop();
    }
}
