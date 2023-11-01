package io.risf.sales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.risf.sales.service.outputter.impl.DoubleSerializer;

import java.util.List;

/**
 * The output to be used by the printer
 *
 * @param items    the list of the receipt items containing all the data necessary for the output
 * @param total    the sum of all items' prices including tax
 * @param taxTotal the sum of all taxable items (doesn't include base prices)
 */
public record ReceiptOutput(List<ReceiptItemOutput> items,
                            @JsonSerialize(using = DoubleSerializer.class)
                            @JsonProperty("salesTotal")
                            double total,
                            @JsonSerialize(using = DoubleSerializer.class)
                            @JsonProperty("salesTax")
                            double taxTotal) {
}
