package io.risf.sales.service.tax.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.model.Category;
import io.risf.sales.model.Product;
import io.risf.sales.service.tax.TaxAmountProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

@SpringBootTest
class DefaultTaxCalculatorTest {

    @MockBean
    private TaxAmountProvider importTaxAmountProvider;

    @Autowired
    private DefaultTaxCalculator taxCalculator;

    private static Stream<Arguments> invalidStoredProducts() {
        Product product = new Product(1L, "Bottle of perfume", null);

        return Stream.of(
                arguments(new ReceiptItem("Bottle of perfume", 20d, 2, false), null),
                arguments(new ReceiptItem("Bottle of perfume", 9.5, 4, false), product)
        );
    }

    @Test
    void testCalculateImportedTaxOk() {
        double expectedOutput = 8.55;
        ReceiptItem receiptItem = new ReceiptItem("Bottle of imported perfume", 21.32, 2, true);
        Category category = new Category(4L, "other", 5);
        Product product = new Product(1L, "Bottle of perfume", category);

        when(importTaxAmountProvider.getTaxPercentage()).thenReturn(15);

        double totalTax = taxCalculator.calculateTax(receiptItem, product);
        assertEquals(expectedOutput, totalTax, "The calculated tax should be correct");
    }

    @Test
    void testCalculateNonImportedTaxOk() {
        double expectedOutput = 1.1;
        ReceiptItem receiptItem = new ReceiptItem("Bottle of perfume", 21.31, 1, false);
        Category category = new Category(1L, "other", 5);
        Product product = new Product(1L, "Bottle of perfume", category);

        double totalTax = taxCalculator.calculateTax(receiptItem, product);

        verify(importTaxAmountProvider, never()).getTaxPercentage();
        assertEquals(expectedOutput, totalTax, "The calculated tax should be correct");
    }

    @ParameterizedTest
    @CsvSource({"21.30, 21.30", "11.51, 11.55", "10.49, 10.50"})
    void testRoundUpTaxOK(double value, double expected) {
        double output = taxCalculator.roundTax(value);
        assertEquals(expected, output, "The tax should round up correctly");
    }

    @ParameterizedTest
    @MethodSource("invalidStoredProducts")
    void testInvalidStoredProducts(ReceiptItem receiptItem, Product product) {
        String expectedOutput = "Invalid product data";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> taxCalculator.calculateTax(receiptItem, product));

        assertEquals(expectedOutput, exception.getMessage(), "Should throw the correct exception and message");
    }
}