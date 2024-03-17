package org.example;

import java.io.FileInputStream;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Main {
    static Logger logger;

    static {
        try(FileInputStream config = new FileInputStream("src/main/resources/log.properties")){
            LogManager.getLogManager().readConfiguration(config);
            logger = Logger.getLogger(Interpretator.class.getName());
        } catch (Exception e){
            System.err.printf("Error in opening log file");
        }
    }
    public static void main(String[] args) {
        Interpretator inter = new Interpretator();
        try (Scanner scanner = new Scanner(new File("in.txt"))) {
            int result = inter.Reading(scanner);
            logger.log(Level.INFO, "Чтение выполнено");
            System.out.printf("%d", result);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (Factory.PropertiesLoadingException e) {
            System.err.println(e.getMessage());
        }
    }
}