package org.example;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;
import java.io.IOException;
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
            StackOperations(word, stack, scanner);
        }
        if (!stack.empty()){
            return stack.peek();
        } else {
            return 0;
        }
    }

    public static void StackOperations(String word, Stack<Integer> stack, Scanner scanner){
        if(!Factory.getInstance().FindOperation(word)){
            try {
                stack.push(Integer.parseInt(word));
                logger.log(Level.INFO, "Положили на стек " + word);
            } catch (NumberFormatException e){
                System.err.printf("Error: Invalid number - %s%n", word);
            }
        } else {
            Factory.getInstance().CreateOperation(word).Operation(stack, scanner);
            logger.log(Level.INFO, "Выполнили операцию " + word);
        }
    }
}
