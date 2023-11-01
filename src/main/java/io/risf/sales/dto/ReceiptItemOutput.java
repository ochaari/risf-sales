package io.risf.sales.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.risf.sales.service.outputter.impl.DoubleSerializer;

/**
 * Represents a processed receipt item
 *
 * @param itemName       the name of the item with the proper formatting (ie. moving imported to the front if it exists
 * @param totalBeforeTax the quantity * price without including any tax calculations
 * @param totalTax       tax per item * quantity
 * @param quantity       the number of items purchased
 */
public record ReceiptItemOutput(String itemName,
                                @JsonSerialize(using = DoubleSerializer.class) double totalBeforeTax,
                                @JsonSerialize(using = DoubleSerializer.class) double totalTax,
                                int quantity) {
}
