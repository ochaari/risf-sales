package io.risf.sales.service.tax.impl;

import io.risf.sales.service.tax.TaxAmountProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Used to provide the importation tax value defined in the application.properties file
 */
@Component
public class ImportTaxAmountProvider extends TaxAmountProvider {

    @Override
    public Integer getTaxPercentage() {
        return taxPercentage;
    }

    @Override
    @Value("${io.risf.importation.tax.percent}")
    public void setTaxPercentage(Integer taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
}
