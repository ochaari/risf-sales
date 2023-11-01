package io.risf.sales.service.validator;

/**
 * Wrapper class containing the validation rule to be used for validation
 *
 * @param <T> the Object to be validated
 */
public class Validator<T> {
    private ValidationRule<T> rule;

    public Validator(ValidationRule<T> rule) {
        this.rule = rule;
    }

    public static <T> Validator<T> create(ValidationRule<T> rule) {
        return new Validator<>(rule);
    }

    public boolean isValid(T data) {
        return rule.isValid(data);
    }

}
