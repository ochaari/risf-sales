package io.risf.sales.service.validator;

import io.risf.sales.model.Category;
import io.risf.sales.model.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ProductValidationRuleTest {

    private final ProductValidationRule productValidationRule;

    public ProductValidationRuleTest() {
        this.productValidationRule = new ProductValidationRule();
    }

    private static Stream<Arguments> productsInput() {
        Category category = new Category(4L, "other", 5);
        return Stream.of(
                arguments(null, false),
                arguments(new Product(1L, "Bottle of perfume", category), true),
                arguments(new Product(1L, "Bottle of perfume", null), false)
        );
    }

    @ParameterizedTest
    @MethodSource("productsInput")
    void testProductValidationOK(Product product, boolean expectedResult) {

        boolean validationResult = productValidationRule.isValid(product);

        assertEquals(expectedResult, validationResult, "Should be able to validated Products correctly");
    }
}