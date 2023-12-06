package io.risf.sales.service.rounder;

public class DefaultRoundingStrategy implements RoundingStrategy{
    @Override
    public double round(double amount) {
        return Math.ceil(amount * 20) / 20;
    }
}
