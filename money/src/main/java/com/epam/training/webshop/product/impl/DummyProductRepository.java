package com.epam.training.webshop.product.impl;

import com.epam.training.webshop.product.Product;
import com.epam.training.webshop.product.ProductRepository;

import java.util.List;
import java.util.Optional;

public class DummyProductRepository implements ProductRepository {

    public static final List<Product> PRODUCTS = List.of(
            SimpleProduct.builder("Alma").withNetPrice(100).withPackaging("1kg").build(),
            SimpleProduct.builder("Milka").withNetPrice(500).withPackaging("100gr").build(),
            SimpleProduct.builder("Pálinka").withNetPrice(1000).withPackaging("1l").build()
    );

    @Override
    public List<Product> getProducts() {
        return PRODUCTS;
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return PRODUCTS.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }
}
