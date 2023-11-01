package io.risf.sales.config;

import io.risf.sales.dto.ReceiptItem;
import io.risf.sales.model.Product;
import io.risf.sales.service.validator.ProductValidationRule;
import io.risf.sales.service.validator.ReceiptItemValidationRule;
import io.risf.sales.service.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Declares the Validation beans that we are using. couldn't use the @Component annotation on these classes,
 * Spring has a problem dealing with these classes since they use Generics
 */
@Configuration
public class ValidatorConfig {
    /**
     * @return A receipt item validator to be injected later
     */
    @Bean
    public Validator<ReceiptItem> receiptItemValidator() {
        return Validator.create(new ReceiptItemValidationRule());
    }

    /**
     * @return A product validator to be injected later
     */
    @Bean
    public Validator<Product> productValidator() {
        return Validator.create(new ProductValidationRule());
    }
}
