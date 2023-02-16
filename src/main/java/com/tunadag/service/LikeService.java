package com.tunadag.service;

import com.tunadag.dto.request.LikeCreateRequestDto;
import com.tunadag.dto.response.LikeCreateResponseDto;
import com.tunadag.exception.CommentAppException;
import com.tunadag.exception.ErrorType;
import com.tunadag.mapper.LikeMapper;
import com.tunadag.repository.ILikeRepository;
import com.tunadag.repository.entity.Like;
import com.tunadag.repository.entity.Product;
import com.tunadag.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ILikeRepository likeRepository;
    private final UserService userService;
    private final ProductService productService;

    public void saveAll(List<Like> likes){
        likeRepository.saveAll(likes);
    }

    public List<Like> findAll(){
        return likeRepository.findAll();
    }

    public boolean controlLikes(List<Like> likes, long productId){
        AtomicBoolean control = new AtomicBoolean(true);
        for (Like like : likes) {
            if (like.getProduct().getId() == productId){
                control.set(false);
                break;
            }
        }
        /*        likes.stream().forEach(l -> {

            if (l.getProduct().getId() == productId) {
                control.set(false);
            }

        });*/
        return control.get();
    }

    public Like save(long userId, long productId){
        Optional<User> user = userService.findById(userId);
        Optional<Product> product = productService.findById(productId);

        if (user.isPresent() && product.isPresent()){
            if (controlLikes(user.get().getLikes(), productId)){
                Like like = new Like();
                try {
                    like.setUser(user.get());
                    like.setProduct(product.get());
                    likeRepository.save(like);
                    user.get().getLikes().add(like);
                    product.get().getLikes().add(like);
                    userService.save(user.get());
                    productService.save(product.get());
                    return like;
                } catch (Exception e){
                    throw new RuntimeException(e.getMessage());
                }
            } else {
                throw new CommentAppException(ErrorType.LIKE_ALREADY_EXIST);
            }
        } else {
            throw new CommentAppException(ErrorType.LIKE_NOT_CREATED);
        }
    }

    public LikeCreateResponseDto save2(LikeCreateRequestDto dto){
        Optional<User> user = userService.findById(dto.getUserId());
        Optional<Product> product = productService.findById(dto.getProductId());
        if (user.isPresent() && product.isPresent()){
            if (controlLikes(user.get().getLikes(), dto.getProductId())){
                Like like = new Like();
                try {
                    like.setUser(user.get());
                    like.setProduct(product.get());
                    likeRepository.save(like);
                    user.get().getLikes().add(like);
                    product.get().getLikes().add(like);
                    userService.save(user.get());
                    productService.save(product.get());
                    return LikeMapper.INSTANCE.toLikeResponseDto(dto);
                } catch (Exception e){
                    throw new RuntimeException(e.getMessage());
                }
            } else {
                throw new CommentAppException(ErrorType.LIKE_ALREADY_EXIST);
            }
        } else {
            throw new CommentAppException(ErrorType.LIKE_NOT_CREATED);
        }
    }

    public Like toLike(LikeCreateRequestDto like){
        Optional<User> userDb = userService.findById(like.getUserId());
        Optional<Product> productDb = productService.findById(like.getProductId());
        if (userDb.isEmpty()){
            throw new CommentAppException(ErrorType.USER_NOT_FOUND, "Kullanıcı bulunamadı");
        }
        if (productDb.isEmpty()){
            throw new CommentAppException(ErrorType.PRODUCT_NOT_FOUND, "Ürün bulunamadı");
        }
        return save3(like, userDb.get(), productDb.get());
    }

    public Like save3(LikeCreateRequestDto dto, User user, Product product){
        if (controlLikes(user.getLikes(), product.getId())){
            Like like = LikeMapper.INSTANCE.toLike(dto);
            System.out.println(like);
            try {
                like.setUser(user);
                like.setProduct(product);
                likeRepository.save(like);
                user.getLikes().add(like);
                product.getLikes().add(like);
                userService.save(user);
                productService.save(product);
                return like;
            } catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new CommentAppException(ErrorType.LIKE_ALREADY_EXIST);
        }
    }

    public void deleteById(long id){
        likeRepository.deleteById(id);
    }
}
