package com.example.farmfarm.Repository.Product;

import com.example.farmfarm.Entity.ProductEntity;
import com.example.farmfarm.Entity.QProductEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository{
    QProductEntity product = QProductEntity.productEntity;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductEntity> findProductList(boolean isAuction, String criteria, String keyword) {
        return queryFactory.selectFrom(product)
                .where(product.auction.eq(isAuction), product.status.eq("yes"), searchKeyword(keyword))
                .orderBy(order(criteria))
                .fetch();
    }

    public OrderSpecifier<?> order(String criteria) {
        if (criteria == null) {
            return product.pId.desc();
        }

        switch (criteria) {
            case "rating":
                return product.rating.desc();
            case "lowPrice":
                return product.price.asc();
            case "highPrice":
                return product.price.desc();
            default:
                return product.pId.desc();
        }
    }

    public BooleanExpression searchKeyword(String keyword) {
        return keyword != null && !keyword.isEmpty() ? product.name.containsIgnoreCase(keyword) : null;
    }

}
