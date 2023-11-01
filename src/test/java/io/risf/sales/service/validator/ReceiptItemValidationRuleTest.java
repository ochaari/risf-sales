package io.risf.sales.service.validator;

import io.risf.sales.dto.ReceiptItem;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ReceiptItemValidationRuleTest {

    private final ReceiptItemValidationRule receiptItemValidationRule;

    public ReceiptItemValidationRuleTest() {
        receiptItemValidationRule = new ReceiptItemValidationRule();
    }

    private static Stream<Arguments> receiptItemsInput() {
        return Stream.of(
                arguments(null, false),
                arguments(new ReceiptItem(null, 21.34, 2, true), false),
                arguments(new ReceiptItem("    ", 21.34, 2, true), false),
                arguments(new ReceiptItem("imported music CD", 0d, 2, true), false),
                arguments(new ReceiptItem("imported music CD", 21.34, 0, true), false),
                arguments(new ReceiptItem("imported music CD", 21.34, 2, true), true)
        );
    }

    @ParameterizedTest
    @MethodSource("receiptItemsInput")
    void testProductValidationOK(ReceiptItem receiptItem, boolean expectedResult) {

        boolean validationResult = receiptItemValidationRule.isValid(receiptItem);

        assertEquals(expectedResult, validationResult, "Should be able to validated receipt items correctly");
    }
}