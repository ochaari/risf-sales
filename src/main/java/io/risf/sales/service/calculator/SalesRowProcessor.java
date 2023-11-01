package io.risf.sales.service.calculator;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.dto.ReceiptItemOutput;
import io.risf.sales.dto.ReceiptOutput;

import java.util.Collection;

/**
 * The contract for the processor of the output receipt
 */
public interface SalesRowProcessor {

    /**
     * takes the parsed row & makes all the necessary calculations for the output
     *
     * @param receiptItem the parse input line
     * @return the receipt item to be used in the receiipt output
     */
    ReceiptItemOutput processItem(ReceiptItem receiptItem);

    /**
     * Loops through all the parsed input receipt items & generated the final object to print
     *
     * @param receiptItemList the list of all the receipt items in the output
     * @return The object to be outputted by the printer
     */
    ReceiptOutput processReceiptItems(Collection<ReceiptItem> receiptItemList);

}
