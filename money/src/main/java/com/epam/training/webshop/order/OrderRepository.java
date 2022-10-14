package com.epam.training.webshop.order;

import com.epam.training.webshop.cart.Cart;

public interface OrderRepository {

    void saveOrder(Cart cart);
}
