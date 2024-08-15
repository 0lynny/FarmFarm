package com.example.farmfarm.Repository.Farm;

import com.example.farmfarm.Entity.FarmEntity;

import java.util.List;

public interface FarmCustomRepository {
    List<FarmEntity> findFarmList(String criteria, String keyword);
}
