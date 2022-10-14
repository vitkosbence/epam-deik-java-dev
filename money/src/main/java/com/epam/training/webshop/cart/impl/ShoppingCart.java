package com.epam.training.webshop.cart.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.exception.UnknownProductException;
import com.epam.training.webshop.coupon.Coupon;
import com.epam.training.webshop.gross.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.product.Product;
import com.epam.training.webshop.product.ProductRepository;
import com.epam.training.webshop.product.impl.SimpleProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCart implements Cart {

    private final List<Product> products;
    private final List<Coupon> coupons;

    private final GrossPriceCalculatorDecorator grossPriceCalculatorDecorator;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ShoppingCart(final OrderRepository orderRepository, final ProductRepository productRepository,
                        final GrossPriceCalculatorDecorator grossPriceCalculatorDecorator, List<Product> products) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.grossPriceCalculatorDecorator = grossPriceCalculatorDecorator;
        this.products = products;
        coupons = new ArrayList<>();
    }

    public ShoppingCart(final OrderRepository orderRepository, final ProductRepository productRepository,
                        final GrossPriceCalculatorDecorator grossPriceCalculatorDecorator) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.grossPriceCalculatorDecorator = grossPriceCalculatorDecorator;
        this.products = new ArrayList<>();
        coupons = new ArrayList<>();
    }

    @Override
    public List<Product> listProducts() {
        return products;
    }

    @Override
    public void addProduct(Product product) {

    }

    //    @Override
    public void addProduct(SimpleProduct product) {
        Optional.of(product)
                .ifPresent(this.products::add);
    }

    @Override
    public void addProduct(final String productName) {
        productRepository.getProductByName(productName)
                .ifPresentOrElse(products::add,
                        () -> {
                            throw new UnknownProductException(productName);
                        }
                );
    }

    @Override
    public void removeProduct(Product productToRemove) {

    }

//    @Override
    public void removeProduct(SimpleProduct productToRemove) {
        products.remove(productToRemove);
    }

    @Override
    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    @Override
    public List<Coupon> getCouponsFromBasket() {
        return coupons;
    }

    @Override
    public double getTotalNetPrice() {
        final double basePrice = getBasePrice();
        final double discount = getDiscountForCoupons();
        return basePrice - discount;
    }

    @Override
    public double getTotalGrossPrice() {
        return grossPriceCalculatorDecorator.getAggregatedGrossPrice(this);
    }

    @Override
    public void order() {
        orderRepository.saveOrder(this);
    }

    @Override
    public double getBasePrice() {
        double basePrice = 0;
        for (final Product currentProduct : products) {
            basePrice += currentProduct.getNetPrice();
        }
        return basePrice;
    }

    @Override
    public double getDiscountForCoupons() {
        final double discount = 0;
//        for (Coupon coupon : coupons) {
//            discount += coupon.getDiscountForProducts(products);
//        }
        return discount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "products=" + products +
                ", coupons=" + coupons +
                ", grossPriceCalculatorDecorator=" + grossPriceCalculatorDecorator +
                ", orderRepository=" + orderRepository +
                '}';
    }
}
