package com.tunadag.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double price;
    private LocalDate expirationDate;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Like> likes = new ArrayList<>();

    @OneToMany
    @Builder.Default
    List<ProductComment> comments = new ArrayList<>();
}
