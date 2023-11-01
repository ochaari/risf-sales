package io.risf.sales.service.calculator.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.service.calculator.PriceCalculator;
import io.risf.sales.service.validator.Validator;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Setter
public class PriceCalculatorImpl implements PriceCalculator {
    private static final Logger logger = LoggerFactory.getLogger(PriceCalculatorImpl.class);

    private final Validator<ReceiptItem> receiptItemValidator;

    public PriceCalculatorImpl(Validator<ReceiptItem> receiptItemValidator) {
        this.receiptItemValidator = receiptItemValidator;
    }

    @Override
    public double calculateTotalWithoutTax(ReceiptItem receiptItem) {
        logger.debug("calculating total without tax");
        if (!receiptItemValidator.isValid(receiptItem)) {
            logger.error("invalid receipt item {}", receiptItem);
            throw new IllegalArgumentException("Invalid receipt data");
        }
        return receiptItem.price() * receiptItem.quantity();
    }


}
