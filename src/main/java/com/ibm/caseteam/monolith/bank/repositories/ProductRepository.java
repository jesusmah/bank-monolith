package com.ibm.caseteam.monolith.bank.repositories;


import com.ibm.caseteam.monolith.bank.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String>{

  Iterable<Product> findAll();
}
