package io.risf.sales.service.calculator.impl;

import io.risf.sales.dto.ReceiptItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
class ItemNameCalculatorImplTest {

    @Autowired
    private ItemNameCalculatorImpl itemNameCalculator;

    private static Stream<Arguments> validTestReceipts() {
        return Stream.of(
                arguments(new ReceiptItem("Box of imported Chocolates", 21.34, 2, true), "imported Box of Chocolates"),
                arguments(new ReceiptItem("BOX of CHOCOLATES", 21.34, 2, false), "BOX of CHOCOLATES"),
                arguments(new ReceiptItem("  imported   Box  of   Chocolates   ", 21.34, 2, true), "imported Box of Chocolates")
        );
    }

    private static Stream<ReceiptItem> invalidTestReceipts() {
        return Stream.of(
                null,
                new ReceiptItem(null, 21.34, 2, true),
                new ReceiptItem("    ", 21.34, 2, true)
        );
    }

    @Test
    void testProcessItemNameWithoutImportedKeywordOK() {
        String input = "Box of imported Chocolates";
        String expectedOutput = "Box of Chocolates";
        ReceiptItem receiptItem = new ReceiptItem(input, 21.34, 2, true);

        String processedItemName = itemNameCalculator.stripImportedKeyword(receiptItem);

        assertEquals(expectedOutput, processedItemName, "Should strip the imported keyword without adding it to the front");
    }

    @ParameterizedTest
    @MethodSource("validTestReceipts")
    void testProcessItemNameWithImportedKeywordOK(ReceiptItem receiptItem, String expectedOutput) {
        String processedItemName = itemNameCalculator.formatItemName(receiptItem);

        assertEquals(expectedOutput, processedItemName, "Should strip the imported keyword and add it to the front");
    }

    @ParameterizedTest
    @MethodSource("invalidTestReceipts")
    void testProcessItemNameWithBadArgumentsIllegalArgumentException(ReceiptItem receiptItem) {
        String expectedOutput = "Invalid receipt input";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> itemNameCalculator.formatItemName(receiptItem));
        assertEquals(expectedOutput, exception.getMessage(), "Should return the correct exception message");
    }

}