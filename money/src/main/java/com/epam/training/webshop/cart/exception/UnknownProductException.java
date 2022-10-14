package com.epam.training.webshop.cart.exception;

public class UnknownProductException extends RuntimeException {
    public UnknownProductException(final String productName) {
        super("There is no Product with name = " + productName);
    }
}
