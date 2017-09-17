package com.bbc;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommandLineReaderTest {

    @Test
    public void readingAnyValueFromConsoleShouldReturnThatValue() {

        String input = "anyValue";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        List<String> read = CommandLineReader.read();

        assertEquals("anyValue", read.get(0));
    }

    @Test
    public void readingAListOfValuesShouldReturnTheSameListOfTheSameValues() {
        String value1 = "value1";
        String value2 = "value2";

        String simulatedUserInput = value1 + System.getProperty("line.separator")
                + value2 + System.getProperty("line.separator");

        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(in);
        List<String> read = CommandLineReader.read();

        assertEquals("value1", read.get(0));
        assertEquals("value2", read.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void doNotReadEndInputWord() {
        String value = "Done";

        InputStream in = new ByteArrayInputStream(value.getBytes());
        System.setIn(in);
        List<String> read = CommandLineReader.read();

        assertEquals("Done", read.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readEverythingUpToEndInputWord() throws IndexOutOfBoundsException{

        String value = "Done";
        String value1 = "value1";
        String value2 = "value2";
        String simulatedUserInput = value1 + System.getProperty("line.separator")
                + value2 + System.getProperty("line.separator")+ value + System.getProperty("line.separator");

        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(in);
        List<String> read = CommandLineReader.read();

        assertEquals("value1", read.get(0));
        assertEquals("value2", read.get(1));
        assertEquals("Done", read.get(2));
    }

}
