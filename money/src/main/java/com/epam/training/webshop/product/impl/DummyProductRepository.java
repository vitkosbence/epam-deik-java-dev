package com.epam.training.webshop.product.impl;

import com.epam.training.webshop.product.Product;
import com.epam.training.webshop.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public class DummyProductRepository implements ProductRepository {


    private List<Product> products;

    public void setProducts() {
       products = List.of(
                SimpleProduct.builder("Alma").withNetPrice(100).withPackaging("1kg").build(),
                SimpleProduct.builder("Milka").withNetPrice(500).withPackaging("100gr").build(),
                SimpleProduct.builder("Pálinka").withNetPrice(1000).withPackaging("1l").build()
        );
    }
//    public static final List<Product> PRODUCTS = List.of(
//            SimpleProduct.builder("Alma").withNetPrice(100).withPackaging("1kg").build(),
//            SimpleProduct.builder("Milka").withNetPrice(500).withPackaging("100gr").build(),
//            SimpleProduct.builder("Pálinka").withNetPrice(1000).withPackaging("1l").build()
//    );

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }
}
