package com.epam.training.webshop.config;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.impl.ShoppingCart;
import com.epam.training.webshop.gross.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.gross.impl.HungarianGrossPriceCalculator;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.order.impl.DummyOrderRepository;
import com.epam.training.webshop.product.ProductRepository;
import com.epam.training.webshop.product.impl.DummyProductRepository;

public final class ApplicationConfig {

    private ApplicationConfig() {
    }

    public static Cart cart() {
        return new ShoppingCart(orderRepository(), productRepository(), grossPriceCalculatorDecorator());
    }

    public static GrossPriceCalculatorDecorator grossPriceCalculatorDecorator() {
        return new HungarianGrossPriceCalculator(GrossPriceCalculatorSingleton.getGrossPriceCalculatorDecorator());
    }

    public static OrderRepository orderRepository() {
        return new DummyOrderRepository();
    }

    public static ProductRepository productRepository() {
        return new DummyProductRepository();
    }
}
