package io.risf.sales.service.validator;

import io.risf.sales.dto.ReceiptItem;
import org.springframework.util.StringUtils;

/**
 * used to validate the fields of the product
 */
public class ReceiptItemValidationRule implements ValidationRule<ReceiptItem> {
    @Override
    public boolean isValid(ReceiptItem receiptItem) {
        return receiptItem != null && receiptItem.price() != 0 && receiptItem.quantity() != 0 && StringUtils.hasText(receiptItem.itemName());
    }
}
