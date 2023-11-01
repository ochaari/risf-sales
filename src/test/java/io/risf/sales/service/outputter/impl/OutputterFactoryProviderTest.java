package io.risf.sales.service.outputter.impl;

import io.risf.sales.service.outputter.OutputType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OutputterFactoryProviderTest {

    @Test
    void testOutputterFactoryProviderFilledOk() {
        OutputterFactoryProvider.registerFactory(OutputType.TEXT.name(), new TextOutputterFactory());
        OutputterFactoryProvider.registerFactory(OutputType.JSON.name(), new JSONOutputterFactory());

        assertNotNull(OutputterFactoryProvider.getFactory(OutputType.TEXT.name()), "Should be able to get the Text Outputter");
        assertNotNull(OutputterFactoryProvider.getFactory(OutputType.JSON.name()), "Should be able to get the JSON Outputter");
        assertNull(OutputterFactoryProvider.getFactory(""), "Should get null");
    }
}