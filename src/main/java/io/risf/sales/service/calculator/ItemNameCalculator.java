package io.risf.sales.service.calculator;

import io.risf.sales.dto.ReceiptItem;

/**
 * The contract for an item name calculator
 * Used to process the items' names & do the necessary calculations to determine the category
 */
public interface ItemNameCalculator {

    /**
     * @param receiptItem The receipt item in the input
     * @return the item name to be printed
     */
    String formatItemName(ReceiptItem receiptItem);

    /**
     * As seen in Input 3, we have to move the "imported" word to the front of the item name.
     * But for the time being, we have to remove the imported keyword to check against the database
     * This method removes the "imported" keyword if it exists
     *
     * @param receiptItem The receipt item in the input
     * @return the item name without the "imported" keyword & the unnecessary white spaces
     */
    String stripImportedKeyword(ReceiptItem receiptItem);
}
