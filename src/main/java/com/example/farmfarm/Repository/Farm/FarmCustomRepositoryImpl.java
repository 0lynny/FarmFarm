package com.example.farmfarm.Repository.Farm;

import com.example.farmfarm.Entity.FarmEntity;
import com.example.farmfarm.Entity.ProductEntity;
import com.example.farmfarm.Entity.QFarmEntity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FarmCustomRepositoryImpl implements FarmCustomRepository {
    QFarmEntity farm = QFarmEntity.farmEntity;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FarmEntity> findFarmList(String criteria, String keyword) {
        return queryFactory.selectFrom(farm)
                .where(searchKeyword(keyword))
                .orderBy(order(criteria))
                .fetch();
    }

    public OrderSpecifier<?> order(String criteria) {
        if (criteria == null) {
            return farm.fId.desc();
        }

        switch (criteria) {
            case "rating":
                return farm.rating.desc();
            case "new":
                return farm.fId.desc();
            default:
                return farm.fId.asc();
        }
    }

    public BooleanExpression searchKeyword(String keyword) {
        return keyword != null && !keyword.isEmpty() ? farm.name.containsIgnoreCase(keyword) : null;
    }

}