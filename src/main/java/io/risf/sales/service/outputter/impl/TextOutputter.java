package io.risf.sales.service.outputter.impl;

import io.risf.sales.dto.ReceiptItemOutput;
import io.risf.sales.dto.ReceiptOutput;
import io.risf.sales.service.outputter.Outputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TextOutputter implements Outputter {
    private static final Logger logger = LoggerFactory.getLogger(TextOutputter.class);

    /**
     * @param receiptOutput the receipt to be printed
     * @return the plain text to be printed on the console
     */
    @Override
    public String output(ReceiptOutput receiptOutput) {
        logger.info("Printing to console as plain text ...");
        StringBuilder output = new StringBuilder();
        for (ReceiptItemOutput itemOutput : receiptOutput.items()) {
            output.append(String.format("%d %s: %.2f%n", itemOutput.quantity(), itemOutput.itemName(), itemOutput.totalBeforeTax() + itemOutput.totalTax()));
        }
        output.append(String.format("Sales Taxes: %.2f\t", receiptOutput.taxTotal()));
        output.append(String.format("Total: %.2f%n", receiptOutput.total()));
        return output.toString();
    }
}
