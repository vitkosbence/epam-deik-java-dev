package com.epam.training.money.impl;

import com.epam.training.money.Cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart implements Cart {

    private final List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    @Override
    public List<Product> listProduct() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }
}
