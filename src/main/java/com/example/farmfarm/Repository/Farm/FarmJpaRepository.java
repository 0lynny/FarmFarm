package com.example.farmfarm.Repository.Farm;

import com.example.farmfarm.Entity.FarmEntity;
import com.example.farmfarm.Entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FarmJpaRepository extends CrudRepository<FarmEntity, Integer> {
    public FarmEntity findByfIdAndStatusLike(Long fId, String status);
    public FarmEntity findByUserAndStatusLike(UserEntity user, String status);
    public List<FarmEntity> findAllByLocationCityAndLocationGu(String locationCity, String locationGu);
}
