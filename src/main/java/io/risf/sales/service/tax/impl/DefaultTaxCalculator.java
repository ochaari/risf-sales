package io.risf.sales.service.tax.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.model.Product;
import io.risf.sales.service.tax.TaxAmountProvider;
import io.risf.sales.service.tax.TaxCalculator;
import io.risf.sales.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Responsible for the calculation of the tax
 */
@Component
public class DefaultTaxCalculator implements TaxCalculator {
    private static final Logger logger = LoggerFactory.getLogger(DefaultTaxCalculator.class);
    private final TaxAmountProvider taxAmountProvider;
    private final Validator<ReceiptItem> receiptItemValidator;
    private final Validator<Product> productValidator;

    public DefaultTaxCalculator(TaxAmountProvider taxAmountProvider, Validator<ReceiptItem> receiptItemValidator, Validator<Product> productValidator) {
        this.taxAmountProvider = taxAmountProvider;
        this.receiptItemValidator = receiptItemValidator;
        this.productValidator = productValidator;
    }

    /**
     * Used to calculate the tax for the items. It has a check to see whether the product is known by the db or not
     *
     * @param receiptItem   The receipt item in the input
     * @param storedProduct The product extracted from the db
     * @return the total tax for the item, taking into account the quantity, the importation tax & the rounding
     */
    @Override
    public double calculateTax(ReceiptItem receiptItem, Product storedProduct) {
        logger.debug("Calculating the tax for {}", receiptItem);
        if (!receiptItemValidator.isValid(receiptItem) || !productValidator.isValid(storedProduct)) {
            throw new IllegalArgumentException("Invalid product data");
        }
        Integer taxPercent = storedProduct.getCategory().getTaxRatePercent();
        if (receiptItem.isImported()) {
            taxPercent += taxAmountProvider.getTaxPercentage();
        }
        double totalTax = receiptItem.price() * receiptItem.quantity() * taxPercent / 100;
        logger.debug("Total tax before rounding {}", totalTax);
        return roundTax(totalTax);
    }

    /**
     * @param taxAmount the tax amount to be rounded
     * @return the tax amount rounded up to the nearest 0.05
     */
    @Override
    public double roundTax(double taxAmount) {
        double roundedTax = Math.ceil(taxAmount * 20) / 20;
        logger.debug("Total tax after rounding {}", roundedTax);
        return roundedTax;
    }
}
