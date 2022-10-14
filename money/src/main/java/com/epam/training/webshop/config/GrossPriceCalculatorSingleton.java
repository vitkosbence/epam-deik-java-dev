package com.epam.training.webshop.config;

import com.epam.training.webshop.gross.GrossPriceCalculator;
import com.epam.training.webshop.gross.impl.GrossPriceCalculatorImpl;

public final class GrossPriceCalculatorSingleton {

    private GrossPriceCalculatorSingleton() {
    }

    /* Eager approach 1. */
//    public static final GrossPriceCalculator GROSS_PRICE_CALCULATOR_DECORATOR = new GrossPriceCalculatorImpl();

    /* Eager approach 2. */
//    public static final GrossPriceCalculator GROSS_PRICE_CALCULATOR_DECORATOR;
//
//    static {
//        GROSS_PRICE_CALCULATOR_DECORATOR = new GrossPriceCalculatorImpl();
//    }

//
//    public static GrossPriceCalculator getGrossPriceCalculator() {
//        return GROSS_PRICE_CALCULATOR_DECORATOR;
//    }

    // Lazy approach
    private static GrossPriceCalculator grossPriceCalculator;

    public static GrossPriceCalculator getGrossPriceCalculatorDecorator() {
        if (grossPriceCalculator == null) {
            grossPriceCalculator = new GrossPriceCalculatorImpl();
        }
        return grossPriceCalculator;
    }
}
