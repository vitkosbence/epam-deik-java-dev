package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.exception.UnknownProductException;
import com.epam.training.webshop.presentation.cli.command.Command;
import com.epam.training.webshop.product.ProductRepository;

public class AddProductCommand implements Command {

    private final ProductRepository productRepository;
    private final Cart cart;
    private final String productNameToAdd;

    public AddProductCommand(ProductRepository productRepository, Cart cart, String productNameToAdd) {
        this.productRepository = productRepository;
        this.cart = cart;
        this.productNameToAdd = productNameToAdd;
    }

    @Override
    public String execute() {
        try {
            cart.addProduct(productNameToAdd);
        } catch (UnknownProductException e) {
            return "No such product";
        }
//        Optional<Product> productToAdd = productRepository.getProductByName(productNameToAdd);
//        if (productToAdd.isEmpty()) {
//            return "No such product";
//        }
//        cart.addProduct(productToAdd.get());
        return "Alright.";
    }
}
