package com.epam.training.webshop.coupon;

import com.epam.training.webshop.product.Product;

import java.util.List;

public interface Coupon {

    String getId();

    double getDiscountForProducts(List<Product> products);
}
