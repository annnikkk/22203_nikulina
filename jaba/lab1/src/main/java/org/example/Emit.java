package org.example;

import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;

public class Emit extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s){
        emptyCheck(stack);
        char charValue = Character.toChars(stack.peek())[0];
        System.out.println(charValue);
    }
}
