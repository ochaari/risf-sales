package io.risf.sales.repository;

import io.risf.sales.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByNameIgnoreCase(String name);
}
