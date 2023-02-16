package com.tunadag.controller;

import com.tunadag.repository.entity.ProductComment;
import com.tunadag.service.ProductCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productcomment")
public class ProductCommentController {

    private final ProductCommentService productCommentService;

    @GetMapping("/findbyproductid")
    public ResponseEntity<List<ProductComment>> findAllByProductId(long productId) {
        return ResponseEntity.ok(productCommentService.findAllByProductId(productId));
    }

    @GetMapping("/findbycommentdateandproductid")
    public ResponseEntity<List<ProductComment>> findAllByCommentDateBetweenAndUserId(String now, String nextDate, Long productId) {
        return ResponseEntity.ok(productCommentService.findAllByCommentDateBetweenAndUserId(now, nextDate, productId));
    }

    @GetMapping("/findbyuserid")
    public ResponseEntity<List<ProductComment>> findAllByUserId(long userId){
        return ResponseEntity.ok(productCommentService.findAllByUserId(userId));
    }

    @GetMapping("/findbycommentdateanduserid")
    public ResponseEntity<List<ProductComment>> findAllByCommentDateBetweenAndUserId(String now, String nextDate, long userId){
        return ResponseEntity.ok(productCommentService.findAllByCommentDateBetweenAndUserId(now, nextDate, userId));
    }

    @GetMapping("/findbycommentcontains")
    public ResponseEntity<List<ProductComment>> findAllByCommentContaining(String comment){
        return ResponseEntity.ok(productCommentService.findAllByCommentContaining(comment));
    }

    @GetMapping("/findbycommentlength")
    public ResponseEntity<List<ProductComment>> findByCommentLength(int length){
        return ResponseEntity.ok(productCommentService.findByCommentLength(length));
    }

    @GetMapping("/findbycommentlength2")
    public ResponseEntity<List<ProductComment>> findByCommentLength2(int length){
        return ResponseEntity.ok(productCommentService.findByCommentLength2(length));
    }

    @GetMapping("/findbycommentlength3")
    public ResponseEntity<List<ProductComment>> findByCommentLength3(int length){
        return ResponseEntity.ok(productCommentService.findByCommentLength3(length));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<ProductComment>> findAll(){
        return ResponseEntity.ok(productCommentService.findAll());
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ProductComment> findById(@PathVariable Long id){
        return ResponseEntity.ok(productCommentService.findById(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(Long id){
        productCommentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/save")
    public ResponseEntity<ProductComment> save(String comment, long productId, long userId){
        return ResponseEntity.ok(productCommentService.save(comment, productId, userId));
    }

}
