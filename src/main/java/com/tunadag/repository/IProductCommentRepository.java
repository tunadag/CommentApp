package com.tunadag.repository;

import com.tunadag.repository.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IProductCommentRepository extends JpaRepository<ProductComment, Long> {

    List<ProductComment> findAllByProductId(long productId);

    List<ProductComment> findAllByCommentDateBetweenAndProductId(LocalDate now, LocalDate nextDate, long productId);

    List<ProductComment> findAllByUserId(long userId);

    List<ProductComment> findAllByCommentDateBetweenAndUserId(LocalDate now, LocalDate nextDate, long userId);

    List<ProductComment> findAllByCommentContaining(String comment);

    @Query("select pc from ProductComment as pc where length(pc.comment)>? 1")
    List<ProductComment> findByCommentLength(int length);

    @Query("select pc from ProductComment as pc where length(pc.comment)>: x")
    List<ProductComment> findByCommentLength2(@Param("x") int length);

    @Query(value = "select * from product_comment as pc where length(pc.comment)>: x", nativeQuery = true)
    List<ProductComment> findByCommentLength3(@Param("x") int length);
}
