package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MulTest{
    @Test
    public void testMulOperation() {
        String input = "90 140 * 15 *";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(189000, res);
    }
}