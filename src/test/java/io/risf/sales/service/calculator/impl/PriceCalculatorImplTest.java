package io.risf.sales.service.calculator.impl;

import io.risf.sales.dto.ReceiptItem;
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
class PriceCalculatorImplTest {

    @Autowired
    private PriceCalculatorImpl priceCalculator;

    private static Stream<Arguments> validTestReceipts() {
        return Stream.of(
                arguments(new ReceiptItem("Bottle of perfume", 20d, 2, true), 40),
                arguments(new ReceiptItem("Bottle of perfume", 11.34, 2, false), 22.68),
                arguments(new ReceiptItem("Bottle of perfume", 9.5, 4, true), 38)
        );
    }

    private static Stream<ReceiptItem> invalidTestReceipts() {
        return Stream.of(
                new ReceiptItem("Bottle of perfume", 0d, 2, true),
                new ReceiptItem("Bottle of perfume", 11.34, 0, false),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("validTestReceipts")
    void testCalculateTotalBeforeTaxOk(ReceiptItem receiptItem, double expected) {

        double output = priceCalculator.calculateTotalWithoutTax(receiptItem);

        assertEquals(expected, output, "The total before tax should be calculated correctly");

    }

    @ParameterizedTest
    @MethodSource("invalidTestReceipts")
    void testInvalidReceipts(ReceiptItem receiptItem) {
        String expectedOutput = "Invalid receipt data";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> priceCalculator.calculateTotalWithoutTax(receiptItem));

        assertEquals(expectedOutput, exception.getMessage(), "Should throw the correct exception and message");
    }

}