package io.risf.sales.service.rounder;

public interface RoundingStrategy {

    /**
     * @param amount to round
     * @return the rounded amount depending on the strategy
     */
    double round(double amount);
}
