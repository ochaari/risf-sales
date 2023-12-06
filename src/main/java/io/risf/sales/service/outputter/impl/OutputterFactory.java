package io.risf.sales.service.outputter.impl;

import io.risf.sales.service.outputter.OutputType;
import io.risf.sales.service.outputter.Outputter;
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
public class OutputterFactory {

    private static final Logger logger = LoggerFactory.getLogger(OutputterFactory.class);

    private static final Map<String, Outputter> factories = new HashMap<>();

    /**
     * Initiate the instances to return
     */
    public OutputterFactory() {
        registerFactory(OutputType.TEXT.name(), new TextOutputter());
        registerFactory(OutputType.JSON.name(), new JSONOutputter());
    }

    /**
     * Adds the outputter to the outputter pool that we can instantiate later
     * @param outputType the name of the outputter (the key)
     * @param outputter the instance of the outputter
     */
    public static void registerFactory(String outputType, Outputter outputter) {
        logger.info("Adding the outputter of type {} to the outputters pool", outputType);
        factories.put(outputType, outputter);
    }

    /**
     * @param outputType type of the desired output
     * @return the outputter if it's known or else null
     */
    public static Outputter getFactory(String outputType) {
        logger.info("Retrieving the outputter of type {}", outputType);
        return factories.get(outputType);
    }
}
