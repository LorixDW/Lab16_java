package com.vyatsu.task14.repositories;

import com.vyatsu.task14.entities.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IProductRepository extends PagingAndSortingRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    @Transactional
    @Modifying
    @Query("update Product p set p.title = ?1, p.price = ?2 where p.id = ?3")
    void updateTitleAndPriceById(String title, int price, @NonNull Long id);
}
