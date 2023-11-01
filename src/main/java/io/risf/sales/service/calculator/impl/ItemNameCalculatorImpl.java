package io.risf.sales.service.calculator.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.service.calculator.ItemNameCalculator;
import io.risf.sales.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ItemNameCalculatorImpl implements ItemNameCalculator {

    private static final Logger logger = LoggerFactory.getLogger(ItemNameCalculatorImpl.class);

    private static final String WHITE_SPACE = " ";

    private static final String IMPORTED_KEYWORD = "imported";

    private final Validator<ReceiptItem> receiptItemValidator;

    public ItemNameCalculatorImpl(Validator<ReceiptItem> receiptItemValidator) {
        this.receiptItemValidator = receiptItemValidator;
    }

    @Override
    public String formatItemName(ReceiptItem receiptItem) {
        logger.debug("Formatting item name");
        if (!receiptItemValidator.isValid(receiptItem)) {
            throw new IllegalArgumentException("Invalid receipt input");
        }
        String strippedItemName = stripImportedKeyword(receiptItem);
        if (receiptItem.isImported()) {
            return IMPORTED_KEYWORD + WHITE_SPACE + strippedItemName;
        }
        return strippedItemName;
    }

    @Override
    public String stripImportedKeyword(ReceiptItem receiptItem) {
        logger.debug("Removing the imported keyword for {}", receiptItem.itemName());
        String result = receiptItem.itemName();
        if (receiptItem.isImported()) {
            result = result.replaceFirst("(?i)" + IMPORTED_KEYWORD, "");
        }
        return removeMultiSpaces(result).trim();
    }

    /**
     * @param text the text to update
     * @return the same input but without the unnecessary white spaces
     */
    private String removeMultiSpaces(String text) {
        logger.debug("Removing unused white space for {}", text);
        return text.replaceAll("\\s+", WHITE_SPACE);
    }
}
