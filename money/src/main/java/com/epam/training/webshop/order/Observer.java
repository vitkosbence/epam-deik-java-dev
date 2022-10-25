package com.epam.training.webshop.order;

import com.epam.training.webshop.cart.Cart;

public interface Observer {

    void notify(Cart cart);
}
