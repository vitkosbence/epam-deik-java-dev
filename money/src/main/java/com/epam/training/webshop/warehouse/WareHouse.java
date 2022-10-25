package com.epam.training.webshop.warehouse;

import com.epam.training.webshop.order.Observer;
import com.epam.training.webshop.product.Product;

import java.util.List;

public interface WareHouse extends Observer {

    void registerOrderedProducts(List<Product> products);
}
