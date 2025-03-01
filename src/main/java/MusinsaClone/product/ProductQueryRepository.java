package MusinsaClone.product;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct product = QProduct.product;

    public ProductQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Product> findAll(String name, Category category, Condition condition, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(product)
                .where(
                        product.isDeleted.isFalse(),
                        product.isPrivate.isFalse(),
                        containsName(name),
                        eqCategory(category),
                        eqCondition(condition)
                )
                .orderBy(
                        product.createdAt.desc(),
                        product.reviewCount.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public BooleanExpression containsName(String name){
        if (name == null || name.isEmpty()){
            return null;
        }
        return product.name.containsIgnoreCase(name);
    }

    public BooleanExpression eqCategory(Category category){
        if (category == null){
            return null;
        }
        return product.category.eq(category);
    }

    public BooleanExpression eqCondition(Condition condition){
        if (condition == null){
            return null;
        }
        return product.productCondition.eq(condition);
    }

}
