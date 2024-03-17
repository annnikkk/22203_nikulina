package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LessTest {
    @Test
    public void testLessOperation() {
        String input = "65 5 - 70 <";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(1, res);

        String input2 = "70 0 - 70 <";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        Scanner scanner2 = new Scanner(inputStream2);
        res = interpretator.Reading(scanner2);
        assertEquals(0, res);

        String input3 = "75 10 <";
        InputStream inputStream3 = new ByteArrayInputStream(input3.getBytes());
        Scanner scanner3 = new Scanner(inputStream3);
        res = interpretator.Reading(scanner3);
        assertEquals(0, res);

        String input4 = "100 <";
        InputStream inputStream4 = new ByteArrayInputStream(input4.getBytes());
        Scanner scanner4 = new Scanner(inputStream4);
        assertThrows(RuntimeException.class, () -> interpretator.Reading(scanner4));
    }
}