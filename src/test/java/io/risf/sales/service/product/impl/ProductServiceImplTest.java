package io.risf.sales.service.product.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.model.Category;
import io.risf.sales.model.Product;
import io.risf.sales.repository.ProductRepository;
import io.risf.sales.service.calculator.ItemNameCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ItemNameCalculator itemNameCalculator;

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void testGetStoreProductOk() {
        String processedItemName = "Bottle of perfume";
        ReceiptItem receiptItem = new ReceiptItem("Bottle of imported perfume", 20d, 2, false);
        Category category = new Category(4L, "other", 5);
        Product product = new Product(1L, "Bottle of perfume", category);

        when(itemNameCalculator.stripImportedKeyword(receiptItem)).thenReturn(processedItemName);
        when(productRepository.findByNameIgnoreCase(processedItemName)).thenReturn(Optional.of(product));

        productService.getStoreProduct(receiptItem);

        assertEquals(1, product.getId(), "Should have the correct ID");
        assertEquals(processedItemName, product.getName(), "Should have the correct name");
        assertEquals(4, product.getCategory().getId(), "Should have the correct category ID");
        assertEquals("other", product.getCategory().getName(), "Should have the correct category name");
        assertEquals(5, product.getCategory().getTaxRatePercent(), "Should have the correct category tax");
    }

    @Test
    void testGetStoreProductIllegalArgumentException() {
        String expectedMessage = "The name of the product is incorrect";
        String processedItemName = "Bottle of perfume";
        ReceiptItem receiptItem = new ReceiptItem("Bottle of imported perfume", 20d, 2, false);

        when(itemNameCalculator.stripImportedKeyword(receiptItem)).thenReturn(processedItemName);
        doThrow(new IllegalArgumentException("The name of the product is incorrect")).when(productRepository).findByNameIgnoreCase(processedItemName);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.getStoreProduct(receiptItem));

        assertEquals(expectedMessage, exception.getMessage(), "Should have the correct exception message");
    }
}