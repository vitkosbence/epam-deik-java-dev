package com.epam.training.webshop.gross.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.gross.GrossPriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HungarianGrossPriceCalculator extends GrossPriceCalculatorDecorator {

    private Double taxRate;

    public HungarianGrossPriceCalculator(GrossPriceCalculator grossPriceCalculator,
                                         @Value("${gross.tax-rate.hun:2}") Double taxRate) {
        super(grossPriceCalculator);
        this.taxRate = taxRate;
    }

    @Override
    public double getAggregatedGrossPrice(final Cart cart) {
        return super.getAggregatedGrossPrice(cart) * taxRate;
    }
}
