package com.tunadag.repository;

import com.tunadag.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceGreaterThan(double price);

    List<Product> findAllByExpirationDateBefore(LocalDate date);

    List<Product> findAllByExpirationDateAfterOrExpirationDateIsNull(LocalDate date);

    List<Product> findAllByExpirationDateGreaterThanEqualOrExpirationDateIsNull(LocalDate date);

    @Query("select p.price, count(p.price) from Product as p  group by p.price")
    Object[] searchByProductPrice();

    @Query("select p.price, count(p.price) from Product as p  group by p.price")
    List<Object> searchByProductPrice2();

    int countAllByExpirationDate(LocalDate date);

    List<Product> findAllByPriceIn(List<Double> prices);

    List<Product> findAllByPriceIn(Double[] prices);

    List<Product> findAllByNameIn(String[] names);

    List<Product> findAllByExpirationDateBetween(LocalDate now, LocalDate nextDate);
}
