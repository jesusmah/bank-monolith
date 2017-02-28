package com.ibm.caseteam.monolith.bank.services;

import com.ibm.caseteam.monolith.bank.model.Product;

public interface ProductService {
    Iterable<Product> listAllProducts();
}
