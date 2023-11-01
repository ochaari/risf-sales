package io.risf.sales.service.outputter.impl;

import io.risf.sales.service.outputter.OutputterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton used to inject all the available outputters
 * It also keeps track of these outputters
 */
@Component
public class OutputterFactoryProvider {

    private static final Logger logger = LoggerFactory.getLogger(OutputterFactoryProvider.class);

    private static final Map<String, OutputterFactory> factories = new HashMap<>();

    public static void registerFactory(String outputType, OutputterFactory factory) {
        logger.info("Adding the outputter of type {} to the outputters pool", outputType);
        factories.put(outputType, factory);
    }

    public static OutputterFactory getFactory(String outputType) {
        logger.info("Retrieving the outputter of type {}", outputType);
        return factories.get(outputType);
    }
}
