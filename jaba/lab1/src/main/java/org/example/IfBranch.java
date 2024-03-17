package org.example;

import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;
import java.util.NoSuchElementException;


public class IfBranch extends Operations{
    @Override
    public void Operation(Stack<Integer> stack, Scanner scanner){
        int if_counter = 1;
        String word = null;
        if(stack.peek() == 0){
            while(if_counter != 0){
                try {
                    word = scanner.next();
                } catch (NoSuchElementException e){
                    throw new NoSuchElementException("incorrect if_branch");
                }
                if(Objects.equals(word, "if")){
                    if_counter += 1;
                }
                if(Objects.equals(word, "then")){
                    try {
                        word = scanner.next();
                    } catch (NoSuchElementException e){
                        throw new NoSuchElementException("incorrect if_branch");
                    }
                    if(Objects.equals(word, ";")) if_counter -= 1;
                }
                if(Objects.equals(word, "else")){
                    while (if_counter != 0){
                        try {
                            word = scanner.next();
                        } catch (NoSuchElementException e){
                            throw new NoSuchElementException("incorrect if_branch");
                        }
                        if(!Objects.equals(word, "then") && !Objects.equals(word, "else")){
                            Interpretator.StackOperations(word, stack, scanner);
                        }
                        if(Objects.equals(word, "then")){
                            try {
                                word = scanner.next();
                            } catch (NoSuchElementException e){
                                throw new NoSuchElementException("incorrect if_branch");
                            }
                            if(Objects.equals(word, ";")) if_counter -= 1;
                        }
                        if(Objects.equals(word, "else")){
                            throw new NoSuchElementException("incorrect if_branch");
                        }
                    }
                }
            }
        } else {
            while(if_counter != 0) {
                try {
                    word = scanner.next();
                } catch (NoSuchElementException e){
                    throw new NoSuchElementException("incorrect if_branch");
                }
                if (!Objects.equals(word, "then") && !Objects.equals(word, "else")) {
                    Interpretator.StackOperations(word, stack, scanner);
                }
                if (Objects.equals(word, "then")) {
                    try {
                        word = scanner.next();
                    } catch (NoSuchElementException e){
                        throw new NoSuchElementException("incorrect if_branch");
                    }
                    if (Objects.equals(word, ";")) if_counter -= 1;
                }
                if (Objects.equals(word, "else")) {
                    while (if_counter != 0) {
                        try {
                            word = scanner.next();
                        } catch (NoSuchElementException e){
                            throw new NoSuchElementException("incorrect if_branch");
                        }
                        if (Objects.equals(word, "then")) {
                            try {
                                word = scanner.next();
                            } catch (NoSuchElementException e){
                                throw new NoSuchElementException("incorrect if_branch");
                            }
                            if (Objects.equals(word, ";")) if_counter -= 1;
                        }
                        if (Objects.equals(word, "else")) {
                            throw new NoSuchElementException("incorrect if_branch");
                        }
                    }
                }
            }
        }
    }
}
