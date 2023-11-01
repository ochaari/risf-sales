package io.risf.sales.service.validator;

interface ValidationRule<T> {
    boolean isValid(T data);
}
