package com.epam.training.webshop.gross.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.gross.GrossPriceCalculator;

public class GrossPriceCalculatorImpl implements GrossPriceCalculator {
    @Override
    public double getAggregatedGrossPrice(final Cart cart) {
        return cart.getTotalNetPrice();
    }
}
