package io.risf.sales.service.calculator;

import io.risf.sales.dto.ReceiptItem;

/**
 * The contract for calculating the tax-free price
 */
public interface PriceCalculator {

    double calculateTotalWithoutTax(ReceiptItem receiptItem);
}
