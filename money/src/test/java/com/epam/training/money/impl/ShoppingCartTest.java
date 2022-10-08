package com.epam.training.money.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class ShoppingCartTest {

    private ShoppingCart underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ShoppingCart();
    }

    @Test
    public void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
        // Given
        List<Product> expectedResult = Collections.emptyList();
        // When
        List<Product> actualResult = underTest.listProduct();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testListProductShouldReturnTheListOfProductsWhenNotEmpty() {
        // Given
        Product product = new Product();
        List<Product> expectedResult = Collections.singletonList(product);
        underTest.addProduct(product);
        // When
        final List<Product> actual = underTest.listProduct();
        // Then
        Assertions.assertEquals(expectedResult, actual);
    }
}