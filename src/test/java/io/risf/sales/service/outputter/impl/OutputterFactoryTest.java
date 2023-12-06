package io.risf.sales.service.outputter.impl;

import io.risf.sales.service.outputter.OutputType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OutputterFactoryTest {

    @Test
    void testOutputterFactoryProviderFilledOk() {
        OutputterFactory.registerFactory(OutputType.TEXT.name(), new TextOutputter());
        OutputterFactory.registerFactory(OutputType.JSON.name(), new JSONOutputter());

        assertNotNull(OutputterFactory.getFactory(OutputType.TEXT.name()), "Should be able to get the Text Outputter");
        assertNotNull(OutputterFactory.getFactory(OutputType.JSON.name()), "Should be able to get the JSON Outputter");
        assertNull(OutputterFactory.getFactory(""), "Should get null");
    }
}