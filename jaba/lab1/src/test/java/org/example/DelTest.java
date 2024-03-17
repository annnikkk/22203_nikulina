package org.example;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class DelTest{
    @Test
    public void testDelOperation() {
        String input = "7208 53 / 10 /";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(13, res);

        String input1 = "10 0 /";
        InputStream inputStream1 = new ByteArrayInputStream(input1.getBytes());
        Scanner scanner1 = new Scanner(inputStream1);
        assertThrows(java.lang.ArithmeticException.class, () -> interpretator.Reading(scanner1));
        }
}