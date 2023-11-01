package io.risf.sales.service.parser;

import io.risf.sales.dto.ReceiptItem;

import java.util.ArrayList;
import java.util.List;

/**
 * The contract used to parse the user's input to determine the values to put in the Receipt
 */
public interface InputParser {

    /**
     * @param inputRow the input row in the console
     * @return the item's name
     */
    String extractItemName(String inputRow);

    /**
     * @param inputRow the input row in the console
     * @return the price of the item
     */
    Double extractPrice(String inputRow);

    /**
     * @param inputRow the input row in the console
     * @return the quantity of items in a single row
     */
    Integer extractQuantity(String inputRow);

    /**
     * @param inputRow the input row in the console
     * @return whether the item is imported
     */
    Boolean isImported(String inputRow);

    /**
     * @param input a multiline text from the console
     * @return a list containing the rows to be processed
     */
    List<String> splitRows(String input);

    /**
     * calls all the implemented methods to extract the receipt items to be processed
     *
     * @param inputText the multiline text from the console
     * @return the list of all the items to be processed
     */
    default List<ReceiptItem> parseRows(String inputText) {
        List<String> inputRows = splitRows(inputText);
        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (String inputRow : inputRows) {
            String itemName = extractItemName(inputRow);
            Double itemPrice = extractPrice(inputRow);
            Integer itemQuantity = extractQuantity(inputRow);
            Boolean isItemImported = isImported(inputRow);
            receiptItems.add(new ReceiptItem(itemName, itemPrice, itemQuantity, isItemImported));
        }
        return receiptItems;
    }
}
