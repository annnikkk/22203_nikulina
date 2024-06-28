package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


class StrTest {

    @Test
    public void testStrOperation() {
        String input = ".\" bla blabla blablabla\"";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Interpretator interpretator = new Interpretator();
        int res = interpretator.Reading(scanner);

        String expectedOutput = "bla blabla blablabla";
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }
}