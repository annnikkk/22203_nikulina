package org.example;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class LoopBranch extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner, OutputStream s) {
        int previous_top = stack.pop();
        int stack_top = stack.pop();
        String word = null;
        ArrayList<String> commands = new ArrayList<String>();
        int do_counter = 1;
        while(do_counter != 0) {
            try {
                word = scanner.next();
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("incorrect cycle");
            }
            if (Objects.equals(word, "loop")) {
                try {
                    word = scanner.next();
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("incorrect cycle");
                }
                if (Objects.equals(word, ";")) do_counter -= 1;
                continue;
            }
            if (Objects.equals(word, "do")) {
                do_counter += 1;
                continue;
            }
            if (!Objects.equals(word, "loop")) commands.add(word);
        }
        for (int i = previous_top; i < stack_top; i++) {
            for(String str : commands){
                if(Objects.equals(str, "i")) Interpretator.StackOperations(Integer.toString(i), stack, scanner);
                else Interpretator.StackOperations(str, stack, scanner);
            }
        }
    }
}
