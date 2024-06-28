package org.example;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Interpretator {
    static Logger logger;

    static {
        try(FileInputStream config = new FileInputStream("src/main/resources/log.properties")){
            LogManager.getLogManager().readConfiguration(config);
            logger = Logger.getLogger(Interpretator.class.getName());
        } catch (Exception e){
            System.err.printf("Error in opening log file");
        }
    }

    public int Reading(Scanner scanner){
        Stack<Integer> stack = new Stack<>();
        while (scanner.hasNext()) {
            String word = scanner.next();
            java.io.OutputStream outputStream;//написать чему она равна
            StackOperations(word, stack, scanner, outputStream);
        }
        if (!stack.empty()){
            return stack.peek();
        } else {
            return 0;
        }
    }

    public static void StackOperations(String word, Stack<Integer> stack, Scanner scanner, OutputStream outputStream){
        if(!Factory.getInstance().FindOperation(word)){
            try {
                stack.push(Integer.parseInt(word));
                logger.log(Level.INFO, "Положили на стек " + word);
            } catch (NumberFormatException e){
                System.err.printf("Error: Invalid number - %s%n", word);
            }
        } else {
            Factory.getInstance().CreateOperation(word).Operation(stack, scanner, outputStream);
            logger.log(Level.INFO, "Выполнили операцию " + word);
        }
    }
}
