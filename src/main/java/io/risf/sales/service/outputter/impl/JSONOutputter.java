package io.risf.sales.service.outputter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.risf.sales.dto.ReceiptOutput;
import io.risf.sales.service.outputter.Outputter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JSONOutputter implements Outputter {
    private static final Logger logger = LoggerFactory.getLogger(JSONOutputter.class);

    /**
     * @param receiptOutput the receipt to be printed
     * @return the json string to be printed on the console
     */
    @SneakyThrows
    @Override
    public String output(ReceiptOutput receiptOutput) {
        logger.info("Printing to console as JSON ...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(receiptOutput);
    }
}
