package org.example;

import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.io.PrintStream;


public class Str extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s) {
        String word = null;
        try {
            word = scanner.next();
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("incorrect string");
        }
        while (word.charAt(word.length()-1) != '"') {
            System.out.print(word + " ");
            try {
                word = scanner.next();
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("incorrect string");
            }
        }
        word = word.substring(0, word.length() - 1);
        PrintStream ps = new PrintStream(s);
        ps.println(word);
        ps.close();
    }
}
