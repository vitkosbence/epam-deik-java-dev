package com.epam.training.webshop.gross;

import com.epam.training.webshop.cart.Cart;

public interface GrossPriceCalculator {

    double getAggregatedGrossPrice(Cart cart);
}
