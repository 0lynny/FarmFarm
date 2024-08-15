package com.example.farmfarm.Repository.Product;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ProductJpaRepository, ProductCustomRepository{
}
