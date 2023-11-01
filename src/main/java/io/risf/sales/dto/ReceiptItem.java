package io.risf.sales.dto;

/**
 * A Pojo representing 1 row of the input
 *
 * @param itemName   the name of the item
 * @param price      the price of 1 item
 * @param quantity   how many items bought
 * @param isImported whether or not is imported, used to decide if we should calculate importation tax or not
 */
public record ReceiptItem(String itemName, double price, Integer quantity, boolean isImported) {
}
