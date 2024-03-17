package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DupTest {
    @Test
    public void testDupOperation() {
        String input = "5 dup +";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(10, res);

        String input2 = "dup";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        Scanner scanner2 = new Scanner(inputStream2);
        assertThrows(RuntimeException.class, () -> interpretator.Reading(scanner2));
    }
}