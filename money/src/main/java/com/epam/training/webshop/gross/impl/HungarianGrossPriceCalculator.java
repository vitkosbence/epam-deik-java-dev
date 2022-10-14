package com.epam.training.webshop.gross.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.gross.GrossPriceCalculator;

public class HungarianGrossPriceCalculator extends GrossPriceCalculatorDecorator {

    public static final double RATE_OF_HUNGARIAN_TAX = 1.27;

    public HungarianGrossPriceCalculator(final GrossPriceCalculator grossPriceCalculator) {
        super(grossPriceCalculator);
    }

    @Override
    public double getAggregatedGrossPrice(final Cart cart) {
        return super.getAggregatedGrossPrice(cart) * RATE_OF_HUNGARIAN_TAX;
    }
}
