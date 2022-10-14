//package com.epam.training.money.impl;
//
//import com.epam.training.webshop.product.impl.SimpleProduct;
//import com.epam.training.webshop.cart.impl.ShoppingCart;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Collections;
//import java.util.List;
//
//class ShoppingCartTest {
//
//    private ShoppingCart underTest;
//
//    @BeforeEach
//    public void setUp() {
//        underTest = new ShoppingCart();
//    }
//
//    @Test
//    public void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
//        // Given
//
//        List<SimpleProduct> expectedResult = Collections.emptyList();
//        // When
//        List<SimpleProduct> actualResult = underTest.listProduct();
//        // Then
//        Assertions.assertEquals(expectedResult, actualResult);
//    }
//
//    @Test
//    public void testListProductShouldReturnTheListOfProductsWhenNotEmpty() {
//        // Given
//        SimpleProduct product = SimpleProduct.SimpleProductBuilder
//        List<SimpleProduct> expectedResult = Collections.singletonList(product);
//        underTest.addProduct(product);
//        // When
//        final List<SimpleProduct> actual = underTest.listProduct();
//        // Then
//        Assertions.assertEquals(expectedResult, actual);
//    }
//}