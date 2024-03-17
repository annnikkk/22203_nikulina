package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinusTest  {
    @Test
    public void testMinusOperation() {
        String input = "2000000 1000000 - 1000000 - -1 -";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(1, res);
    }
}
