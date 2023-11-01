package io.risf.sales.service.tax;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.model.Product;

/**
 * The contract for calculating the tax
 */
public interface TaxCalculator {

    /**
     * Used to calculate the tax for the items
     *
     * @param receiptItem   The receipt item in the input
     * @param storedProduct The product extracted from the db
     * @return the total tax for the item, taking into account the quantity, the importation tax & the rounding
     */
    double calculateTax(ReceiptItem receiptItem, Product storedProduct);

    /**
     * @param taxAmount the tax amount to be rounded
     * @return the tax amount rounded
     */
    double roundTax(double taxAmount);
}
