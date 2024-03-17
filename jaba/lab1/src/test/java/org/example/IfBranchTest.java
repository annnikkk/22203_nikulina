package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class IfBranchTest {
    @Test
    public void testIfOperation() {
        String input = "0 if 2 2 + then ;";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Interpretator interpretator = new Interpretator();

        int res = interpretator.Reading(scanner);
        assertEquals(0, res);

        String input1 = "2 if 2 + then ;";
        InputStream inputStream1 = new ByteArrayInputStream(input1.getBytes());
        Scanner scanner1 = new Scanner(inputStream1);
        res = interpretator.Reading(scanner1);
        assertEquals(4, res);

        String input2 = "0 if 2 2 + else 20 20 + then ;";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        Scanner scanner2 = new Scanner(inputStream2);
        res = interpretator.Reading(scanner2);
        assertEquals(40, res);

        String input3 = "2 if 2 + else 20 20 + then ;";
        InputStream inputStream3 = new ByteArrayInputStream(input3.getBytes());
        Scanner scanner3 = new Scanner(inputStream3);
        res = interpretator.Reading(scanner3);
        assertEquals(4, res);

        String input4 = "0 if then then ;";
        InputStream inputStream4 = new ByteArrayInputStream(input4.getBytes());
        Scanner scanner4 = new Scanner(inputStream4);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner4));

        String input5 = "0 if if then ;";
        InputStream inputStream5 = new ByteArrayInputStream(input5.getBytes());
        Scanner scanner5 = new Scanner(inputStream5);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner5));

        String input6 = "5 if 2 + else 2 - else then ;";
        InputStream inputStream6 = new ByteArrayInputStream(input6.getBytes());
        Scanner scanner6 = new Scanner(inputStream6);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner6));

        String input7 = "5 if 0 if -10000 then ; if 7000 + else 5000 + then ; else 2 - then ;";
        InputStream inputStream7 = new ByteArrayInputStream(input7.getBytes());
        Scanner scanner7 = new Scanner(inputStream7);
        res = interpretator.Reading(scanner7);
        assertEquals(5000, res);

        String input8 = "2 if 2 2 + else 2 2 - then";
        InputStream inputStream8 = new ByteArrayInputStream(input8.getBytes());
        Scanner scanner8 = new Scanner(inputStream8);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner8));

        String input9 = "2 if 2 2 + then";
        InputStream inputStream9 = new ByteArrayInputStream(input9.getBytes());
        Scanner scanner9 = new Scanner(inputStream9);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner9));

        String input10 = "0 if 2 + else 2 - else then ;";
        InputStream inputStream10 = new ByteArrayInputStream(input10.getBytes());
        Scanner scanner10 = new Scanner(inputStream10);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner10));

        String input11 = "0 if if  19 + else 19 - then ;";
        InputStream inputStream11 = new ByteArrayInputStream(input11.getBytes());
        Scanner scanner11 = new Scanner(inputStream11);
        assertThrows(NoSuchElementException.class, () -> interpretator.Reading(scanner11));
    }
}