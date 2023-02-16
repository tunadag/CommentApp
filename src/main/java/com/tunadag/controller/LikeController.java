package com.tunadag.controller;

import com.tunadag.dto.request.LikeCreateRequestDto;
import com.tunadag.dto.response.LikeCreateResponseDto;
import com.tunadag.repository.entity.Like;
import com.tunadag.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/save")
    public ResponseEntity<Like> save(long userId, long productId){
        return ResponseEntity.ok(likeService.save(userId, productId));
    }

    @PostMapping("/save2")
    public ResponseEntity<LikeCreateResponseDto> save2(@RequestBody LikeCreateRequestDto dto){
        return ResponseEntity.ok(likeService.save2(dto));
    }

    @PostMapping("/save3")
    public ResponseEntity<Like> save3(@RequestBody LikeCreateRequestDto dto){
        return ResponseEntity.ok(likeService.toLike(dto));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Like>> findAll(){
        return ResponseEntity.ok(likeService.findAll());
    }
}
