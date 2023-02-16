package com.tunadag.controller;

import com.tunadag.repository.entity.Product;
import com.tunadag.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/gthan")
    public ResponseEntity<List<Product>> gthan(double price){
        return ResponseEntity.ok(productService.findAllByGreaterThan(price));
    }

    @GetMapping("/expirationdatebefore")
    public ResponseEntity<List<Product>> expirationDateBefore(){
        return ResponseEntity.ok(productService.findAllByExpirationDate());
    }

    @GetMapping("/expirationdatebefore2")
    public ResponseEntity<List<Product>> expirationDateBefore2(String date){
        return ResponseEntity.ok(productService.findAllByExpirationDate2(date));
    }

    @GetMapping("/expirationdateafterornull")
    public ResponseEntity<List<Product>> findAllByExpirationDateAfterOrExpirationDateIsNull(String date){
        return ResponseEntity.ok(productService.findAllByExpirationDateAfterOrExpirationDateIsNull(date));
    }

    @GetMapping("/expirationdateafterornullorequal")
    public ResponseEntity<List<Product>> findAllByExpirationDateGreaterThanEqualOrExpirationDateIsNull(String date){
        return ResponseEntity.ok(productService.findAllByExpirationDateGreaterThanEqualOrExpirationDateIsNull(date));
    }

    @GetMapping("/searchbyprice")
    public ResponseEntity<Object[]> searchByProductPrice(){
        return ResponseEntity.ok(productService.searchByProductPrice());
    }

    @GetMapping("/searchbyprice2")
    public ResponseEntity<List<Object>> searchByProductPrice2(){
        return ResponseEntity.ok(productService.searchByProductPrice2());
    }

    @GetMapping("/countbyexpirationdate")
    public ResponseEntity<Integer> countAllByExpirationDate(String date){
        return ResponseEntity.ok(productService.countAllByExpirationDate(date));
    }

    @GetMapping("/findbypricelist")
    public ResponseEntity<List<Product>> findAllByPriceIn(){
        List<Double> prices = new ArrayList<>();
        prices.add(100D);
        prices.add(700D);
        return ResponseEntity.ok(productService.findAllByPriceIn(prices));
    }

    @GetMapping("/findbypricelist2")
    public ResponseEntity<List<Product>> findAllByPriceIn2(Double[] prices){
        return ResponseEntity.ok(productService.findAllByPriceIn(prices));
    }

    @GetMapping("/findbyproductnames")
    public ResponseEntity<List<Product>> findAllByNameIn(){
        String[] names = {"Monster Notebook", "VCount"};
        return ResponseEntity.ok(productService.findAllByNameIn(names));
    }

    @GetMapping("/findbyproductnames2")
    public ResponseEntity<List<Product>> findAllByNameIn(String[] names){
        return ResponseEntity.ok(productService.findAllByNameIn(names));
    }

    @GetMapping("/expirationdatebetweendiscount")
    public ResponseEntity<List<Product>> findAllByExpirationDateBetween(){
        return ResponseEntity.ok(productService.findAllByExpirationDateBetween());
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id).get());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(Long id){
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/save")
    public ResponseEntity<Product> save(String name, double price, String expirationDate){
        return ResponseEntity.ok(productService.save(name, price, expirationDate));
    }

}
