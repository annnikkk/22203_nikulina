package org.example;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Str extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner) {
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
        System.out.println(word);

    }
}
