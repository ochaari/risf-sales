package io.risf.sales.service.product.impl;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.model.Product;
import io.risf.sales.repository.ProductRepository;
import io.risf.sales.service.calculator.ItemNameCalculator;
import io.risf.sales.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to communicate with the repository to make database operations
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ItemNameCalculator itemNameCalculator;

    public ProductServiceImpl(ProductRepository productRepository, ItemNameCalculator itemNameCalculator) {
        this.productRepository = productRepository;
        this.itemNameCalculator = itemNameCalculator;
    }

    @Override
    public Product getStoreProduct(ReceiptItem receiptItem) {
        String productName = itemNameCalculator.stripImportedKeyword(receiptItem);
        logger.info("Fetching product with name {}", productName);
        return productRepository.findByNameIgnoreCase(productName).orElseThrow(() ->
                new IllegalArgumentException("The name of the product is incorrect")
        );
    }
}
