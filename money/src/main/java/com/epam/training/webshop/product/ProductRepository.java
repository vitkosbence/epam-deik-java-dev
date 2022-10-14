package com.epam.training.webshop.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getProducts();

    Optional<Product> getProductByName(String name);

}
