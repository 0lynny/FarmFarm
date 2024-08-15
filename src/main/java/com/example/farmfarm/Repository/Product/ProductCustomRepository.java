package com.example.farmfarm.Repository.Product;

import com.example.farmfarm.Entity.ProductEntity;

import java.util.List;

public interface ProductCustomRepository {

    List<ProductEntity> findProductList(boolean isAuction, String criteria, String keyword);
}
