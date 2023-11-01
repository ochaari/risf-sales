package io.risf.sales.service.outputter;

import io.risf.sales.dto.ReceiptOutput;

/**
 * The contract for the outputter responsible for printing the output
 */
public interface Outputter {

    /**
     * @param receiptOutput the receipt to be printed
     * @return the string to be printed on the console
     */
    String output(ReceiptOutput receiptOutput);
}
