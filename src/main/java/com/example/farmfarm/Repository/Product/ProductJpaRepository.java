package com.example.farmfarm.Repository.Product;

import com.example.farmfarm.Entity.FarmEntity;
import com.example.farmfarm.Entity.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductJpaRepository extends CrudRepository<ProductEntity, Integer> {
    public ProductEntity findBypIdAndStatusLike(long pId, String status);
    public List<ProductEntity> findAllByFarmAndStatusLike(FarmEntity farm, String status);
}