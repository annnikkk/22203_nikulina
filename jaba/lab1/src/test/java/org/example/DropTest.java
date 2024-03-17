package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DropTest {
    @Test
    public void testDropOperation() {
        String input = " 10 5 7 8 drop";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(7, res);

        String input2 = "drop";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        Scanner scanner2 = new Scanner(inputStream2);
        assertThrows(RuntimeException.class, () -> interpretator.Reading(scanner2));
    }
}