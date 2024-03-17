package org.example;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlusTest {
    @Test
    public void testPlusOperation() {
        String input = "100000 2000000 +";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(2100000, res);

        String input1 = "0 +";
        InputStream inputStream1 = new ByteArrayInputStream(input1.getBytes());
        Scanner scanner1 = new Scanner(inputStream1);
        assertThrows(RuntimeException.class, () -> interpretator.Reading(scanner1));
    }
}