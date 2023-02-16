package com.tunadag.service;

import com.tunadag.exception.CommentAppException;
import com.tunadag.exception.ErrorType;
import com.tunadag.repository.IProductCommentRepository;
import com.tunadag.repository.entity.Product;
import com.tunadag.repository.entity.ProductComment;
import com.tunadag.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.PushBuilder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCommentService {

    private final IProductCommentRepository productCommentRepository;
    private final ProductService productService;
    private final UserService userService;

    public void saveAll(List<ProductComment> pc1){
        productCommentRepository.saveAll(pc1);
    }

    public List<ProductComment> findAllByProductId(long productId){
        return productCommentRepository.findAllByProductId(productId);
    }

    public List<ProductComment> findAllByCommentDateBetweenAndProductId(String now, String nextDate, long productId){
        return productCommentRepository.findAllByCommentDateBetweenAndProductId(LocalDate.parse(now),
                LocalDate.parse(nextDate), productId);
    }

    public List<ProductComment> findAllByUserId(long userId){
        return productCommentRepository.findAllByUserId(userId);
    }

    public List<ProductComment> findAllByCommentDateBetweenAndUserId(String now, String nextDate, Long userId){
        return productCommentRepository.findAllByCommentDateBetweenAndUserId(LocalDate.parse(now),
                LocalDate.parse(nextDate), userId);
    }

    public List<ProductComment> findAllByCommentContaining(String comment){
        return productCommentRepository.findAllByCommentContaining(comment);
    }

    public List<ProductComment> findByCommentLength(int length){
        return productCommentRepository.findByCommentLength(length);
    }

    public List<ProductComment> findByCommentLength2(int length){
        return productCommentRepository.findByCommentLength2(length);
    }

    public List<ProductComment> findByCommentLength3(int length){
        return productCommentRepository.findByCommentLength3(length);
    }

    public List<ProductComment> findAll(){
        return productCommentRepository.findAll();
    }

    public ProductComment findById(Long id){
        Optional<ProductComment> productComment = productCommentRepository.findById(id);
        if (productComment.isPresent()){
            return productComment.get();
        } else {
            throw new CommentAppException(ErrorType.PRODUCTCOMMENT_NOT_FOUND);
        }
    }

    public void deleteById(Long id){
        Optional<ProductComment> productComment = productCommentRepository.findById(id);
        if (productComment.isPresent()){
            productCommentRepository.deleteById(id);
        } else {
            throw new CommentAppException(ErrorType.PRODUCTCOMMENT_NOT_FOUND);
        }
    }

    public ProductComment save(String comment, long productId, long userId){
        Optional<Product> product = productService.findById(productId);
        Optional<User> user = userService.findById(userId);

        if (product.isPresent() && user.isPresent()){
            try {
                return productCommentRepository.save(ProductComment.builder()
                                .comment(comment)
                                .product(product.get())
                                .user(user.get())
                                .build());
            } catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new CommentAppException(ErrorType.PRODUCT_NOT_CREATED);
        }
    }


}
