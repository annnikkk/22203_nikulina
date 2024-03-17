package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ModTest {
    @Test
    public void testModOperation() {
        String input = "7208 10 mod 5 mod";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(3, res);

        String input1 = "10 0 mod";
        InputStream inputStream1 = new ByteArrayInputStream(input1.getBytes());
        Scanner scanner1 = new Scanner(inputStream1);
        assertThrows(java.lang.ArithmeticException.class, () -> interpretator.Reading(scanner1));

        String input2 = "0 mod";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        Scanner scanner2 = new Scanner(inputStream2);
        assertThrows(RuntimeException.class, () -> interpretator.Reading(scanner2));
    }
}