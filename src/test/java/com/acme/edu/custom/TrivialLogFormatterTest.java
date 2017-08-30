package com.acme.edu.custom;

import com.acme.edu.TrivialLogFormatter;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrivialLogFormatterTest {
    @Test(expected = IllegalArgumentException.class)
    public void nullArgumentTest() {
        new TrivialLogFormatter().format(null);
    }

    @Test
    public void testFormatterIsActuallyTrivial() {
        String source = "some string";
        TrivialLogFormatter formatter = new TrivialLogFormatter();
        String result = formatter.format(source);
        assertEquals("strings before and after formatting should be equal", source, result);
    }

    @Test
    public void reusabilityTest() {
        String source1 = "a";
        String source2 = "b";
        String source3 = "c";
        TrivialLogFormatter formatter = new TrivialLogFormatter();

        String result1 = formatter.format(source1);
        String result2 = formatter.format(source2);
        String result3 = formatter.format(source3);

        boolean condition = source1.equals(result1) && source2.equals(result2) && source3.equals(result3);

        assertTrue("formatter should behave the same way after being used", condition);
    }

    @Test
    public void nonStateSharingTest() {
        String source1 = "a";
        String source2 = "b";
        TrivialLogFormatter formatter1 = new TrivialLogFormatter();
        TrivialLogFormatter formatter2 = new TrivialLogFormatter();

        String result1 = formatter1.format(source1);
        String result2 = formatter2.format(source2);

        assertEquals("formatter shouldn't share its state with another formatters", source1, result1);
        assertEquals("formatter shouldn't share its state with another formatters", source2, result2);
    }
}