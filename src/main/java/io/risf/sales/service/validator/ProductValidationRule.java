package io.risf.sales.service.validator;

import io.risf.sales.model.Product;

/**
 * used to validate the fields of the product
 */
public class ProductValidationRule implements ValidationRule<Product> {
    @Override
    public boolean isValid(Product product) {
        return product != null && product.getCategory() != null;
    }
}
