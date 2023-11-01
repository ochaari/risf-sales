package io.risf.sales.service.calculator.impl;

import io.risf.sales.service.tax.TaxAmountProvider;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImportTaxAmountProviderTest {

    @Autowired
    private TaxAmountProvider taxAmountProvider;

    @Test
    @Order(1)
    void testGetTaxPercentageOk() {
        assertEquals(15, taxAmountProvider.getTaxPercentage(), "Should obtain the value 15 from the application.properties");
    }

    @Test
    @Order(2)
    void testSetTaxPercentage() {
        ReflectionTestUtils.setField(taxAmountProvider, "taxPercentage", 5);
        assertEquals(5, taxAmountProvider.getTaxPercentage(), "Should update correctly to 5");
    }

}