package com.epam.training.webshop;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.config.ApplicationConfig;

/* Entry point of the application */
public class WebShopApplication {

    public static void main(String[] args) {
        final Cart cart = ApplicationConfig.cart();
        cart.addProduct("Alma");
        cart.addProduct("Milka");
        cart.order();
    }
}
