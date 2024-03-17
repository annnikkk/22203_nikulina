package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LoopBranchTest {
    @Test
    public void testIfOperation() {
        String input = "10 5 do i loop ; + + + +";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(35, res);

        String input2 = "10 15 do i do loop ;";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        Scanner scanner2 = new Scanner(inputStream2);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner2));

        String input3 = "10 15 do i i loop ";
        InputStream inputStream3 = new ByteArrayInputStream(input3.getBytes());
        Scanner scanner3 = new Scanner(inputStream3);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner3));

        String input4 = "10 5 do i i + loop ;";
        InputStream inputStream4 = new ByteArrayInputStream(input4.getBytes());
        Scanner scanner4 = new Scanner(inputStream4);
        res = interpretator.Reading(scanner4);
        assertEquals(18, res);
    }
}