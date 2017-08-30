package com.acme.edu.custom;

import com.acme.edu.command.BooleanLoggerStrategy;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanLoggerStrategyTest {
    @Test
    public void prefixTest() {
        String prefix = new BooleanLoggerStrategy().getPrefix();

        assertEquals("boolean prefix should be 'primitive'", "primitive", prefix);
    }

    @Test
    public void doesNotRequireCollectionTest() {
        boolean requiresCollection = new BooleanLoggerStrategy().requiresCollection();

        assertEquals("boolean doesn't require collection", false, requiresCollection);
    }

    @Test
    public void collectionArgumentIsUnusedTest() {

    }
}