package io.risf.sales.service.tax;

public abstract class TaxAmountProvider {
    protected Integer taxPercentage;

    public abstract Integer getTaxPercentage();

    public abstract void setTaxPercentage(Integer taxPercentage);
}
