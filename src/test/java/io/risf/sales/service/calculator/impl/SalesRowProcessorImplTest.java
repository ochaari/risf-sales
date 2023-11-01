package io.risf.sales.service.calculator.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.dto.ReceiptItemOutput;
import io.risf.sales.dto.ReceiptOutput;
import io.risf.sales.model.Category;
import io.risf.sales.model.Product;
import io.risf.sales.service.calculator.ItemNameCalculator;
import io.risf.sales.service.calculator.PriceCalculator;
import io.risf.sales.service.product.ProductService;
import io.risf.sales.service.tax.TaxCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SalesRowProcessorImplTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private PriceCalculator priceCalculator;

    @MockBean
    private ItemNameCalculator itemNameCalculator;

    @MockBean
    private TaxCalculator taxCalculator;

    @Autowired
    private SalesRowProcessorImpl salesRowCalculator;

    private static Stream<ReceiptItem> invalidTestReceipts() {
        return Stream.of(
                null,
                new ReceiptItem(null, 21.34, 2, true),
                new ReceiptItem("    ", 21.34, 2, true),
                new ReceiptItem("imported music CD", 0d, 2, true),
                new ReceiptItem("imported music CD", 21.34, 0, true)
        );
    }

    @Test
    void testProcessItemOk() {
        String itemName = "Bottle of perfume";
        double totalBeforeTax = 40d;
        double totalTax = 2;
        int quantity = 2;
        ReceiptItem receiptItem = new ReceiptItem(itemName, 20d, quantity, false);
        Category category = new Category(4L, "other", 5);
        Product product = new Product(1L, "Bottle of perfume", category);

        when(productService.getStoreProduct(receiptItem)).thenReturn(product);
        when(itemNameCalculator.formatItemName(receiptItem)).thenReturn(itemName);
        when(priceCalculator.calculateTotalWithoutTax(receiptItem)).thenReturn(totalBeforeTax);
        when(taxCalculator.calculateTax(receiptItem, product)).thenReturn(totalTax);

        ReceiptItemOutput receiptItemOutput = salesRowCalculator.processItem(receiptItem);

        assertEquals(itemName, receiptItemOutput.itemName(), "Should have the correct name");
        assertEquals(totalBeforeTax, receiptItemOutput.totalBeforeTax(), "Should have the correct total before tax");
        assertEquals(totalTax, receiptItemOutput.totalTax(), "Should have the correct tax value");
        assertEquals(quantity, receiptItemOutput.quantity(), "Should have the correct quantity");

    }

    @Test
    void testProcessReceiptItems() {
        var receiptItems = new ArrayList<ReceiptItem>();
        Category category = new Category(4L, "other", 5);
        Product product = new Product(1L, "Bottle of perfume", category);
        receiptItems.add(new ReceiptItem("Bottle of perfume", 20d, 2, false));
        receiptItems.add(new ReceiptItem("imported music CD", 60d, 1, true));

        when(productService.getStoreProduct(any(ReceiptItem.class))).thenReturn(product);
        when(itemNameCalculator.formatItemName(any(ReceiptItem.class))).thenReturn("Bottle of perfume", "imported music CD");
        when(priceCalculator.calculateTotalWithoutTax(any(ReceiptItem.class))).thenReturn(40d, 120d);
        when(taxCalculator.calculateTax(any(ReceiptItem.class), any(Product.class))).thenReturn(2d, 9d);

        ReceiptOutput receiptOutput = salesRowCalculator.processReceiptItems(receiptItems);

        assertEquals(2, receiptOutput.items().size(), "Should have a list of 2 items");
        assertEquals(11, receiptOutput.taxTotal(), "Should have the correct total tax");
        assertEquals(171, receiptOutput.total(), "Should have the correct total");
    }

    @ParameterizedTest
    @MethodSource("invalidTestReceipts")
    void testProcessItemNameWithBadArgumentsIllegalArgumentException(ReceiptItem receiptItem) {
        String expectedOutput = "Invalid receipt input";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> salesRowCalculator.processItem(receiptItem));
        assertEquals(expectedOutput, exception.getMessage(), "Should return the correct exception message");
    }
}