package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Factory {

    static Logger logger;

    static {
        try(FileInputStream config = new FileInputStream("src/main/resources/log.properties")){
            LogManager.getLogManager().readConfiguration(config);
            logger = Logger.getLogger(Interpretator.class.getName());
        } catch (Exception e){
            System.err.printf("Error in opening log file");
        }
    }
    private HashMap<String, Class<? extends Operations>> CallbackMap = new HashMap<>();

    private static final Factory opFactory = new Factory();

    private Factory() {
        Properties prop = new Properties();
        try {
            prop.load(Main.class.getResourceAsStream("/factory.properties"));
            for (String operation : prop.stringPropertyNames()) {
                String className = prop.getProperty(operation);
                try {
                    RegisterOperation(operation, (Class<? extends Operations>) Class.forName(className));
                    logger.log(Level.INFO, "Операция " + operation + " зарегистрирована.");
                } catch (ClassNotFoundException e) {
                    System.err.printf("Error: Invalid name of class - %s%n", className);
                }

            }
        } catch (IOException e) {
            throw new PropertiesLoadingException("Error loading properties file: " + e.getMessage());
        }
    }

    public static Factory getInstance(){
        return opFactory;
    }

    public void RegisterOperation(String opName, Class<? extends Operations> opClass){
        CallbackMap.put(opName, opClass);
    }

    public class PropertiesLoadingException extends RuntimeException {
        public PropertiesLoadingException(String message){
            super(message);
        }
    }

    public boolean FindOperation(String opName){
        return CallbackMap.containsKey(opName);
    }

    public Operations CreateOperation(String opName){
        Class<? extends Operations> opClass = CallbackMap.get(opName);
        try {
            return opClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            throw new RuntimeException("Operation creation error");
        }
    }
}
