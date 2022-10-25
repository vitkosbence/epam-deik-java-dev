package com.epam.training.webshop.order.confirmation;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.order.Observer;

public interface OrderConfirmationService extends Observer {

    void sendOrderConfirmation(Cart cart);
}
