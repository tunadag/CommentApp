package com.tunadag.service;

import com.tunadag.dto.request.UserCreateRequestDto;
import com.tunadag.dto.response.UserCreateResponseDto;
import com.tunadag.exception.CommentAppException;
import com.tunadag.exception.ErrorType;
import com.tunadag.mapper.UserMapper;
import com.tunadag.repository.IUserRepository;
import com.tunadag.repository.entity.Product;
import com.tunadag.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final ProductService productService;

    public List<User> findOrderByName(){
        return userRepository.findAllByOrderByName();
    }

    public Optional<List<User>> findAllByNameContains(String value){
        Optional<List<User>> list = userRepository.findAllOptionalByNameContainingIgnoreCase(value);
        if (list.get().size() == 0){
            throw new CommentAppException(ErrorType.VALUE_NOT_FOUND_IN_NAMES);
        } else {
            // Optional.ofNullable(list);
            // return Optional.empty();
            return list;
        }
    }

    public Optional<List<User>> findAllByEmailContains(String value){
        Optional<List<User>> list = userRepository.findAllOptionalByEmailContainingIgnoreCase(value);
        if (list.get().size() == 0){
            throw new CommentAppException(ErrorType.VALUE_NOT_FOUND_IN_EMAILS);
        } else {
            return list;
        }
    }

    public Optional<List<User>> findAllByEmailEnding(String value){
        Optional<List<User>> list = userRepository.findAllOptionalByEmailEndingWithIgnoreCase(value);
        if (list.get().size() == 0){
            throw new CommentAppException(ErrorType.VALUE_NOT_FOUND_IN_EMAILS);
        } else {
            return list;
        }
    }

    public void saveAll(List<User> user){
        userRepository.saveAll(user);
    }

    public Optional<User> login(String email, String password){
        Optional<User> optionalUser = userRepository.findOptionalByEmailAndPassword(email, password);
        if (optionalUser.isPresent()){
            return optionalUser;
        }
        throw new CommentAppException(ErrorType.USER_NOT_FOUND);
    }

    public List<User> passwordControl(int length){
        return userRepository.controlPassword(length);
    }

    public List<User> passwordControl2(int length){
        return userRepository.controlPassword2(length);
    }

    public User save(String name, String surName, String email, String password){
        try {
            User user = User.builder()
                    .name(name)
                    .surName(surName)
                    .email(email)
                    .password(password)
                    .build();
            return userRepository.save(user);
        } catch (Exception e) {
            throw new CommentAppException(ErrorType.USER_NOT_CREATED);
        }
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user;
        } else {
            throw new CommentAppException(ErrorType.USER_NOT_FOUND);
        }
    }

    public void deleteById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        } else {
            throw new CommentAppException(ErrorType.USER_NOT_FOUND);
        }
    }

    public List<Long> getFavProducts(long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new CommentAppException(ErrorType.USER_NOT_FOUND);
        } else {
            return user.get().getFavProducts();
        }
    }

    public void addFavProduct(long userId, long productId){
        Optional<Product> product = productService.findById(productId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new CommentAppException(ErrorType.USER_NOT_FOUND);
        }
        if (product.isEmpty()){
            throw new CommentAppException(ErrorType.PRODUCT_NOT_FOUND);
        }
        if (user.get().getFavProducts().contains(productId)){
            throw new CommentAppException(ErrorType.PRODUCT_ALREADY_EXISTS_IN_FAVOURITE_LIST);
        } else {
            user.get().getFavProducts().add(productId);
            userRepository.save(user.get());
        }
    }

    public UserCreateResponseDto saveWithDto(UserCreateRequestDto dto){
        User user = User.builder()
                .userType(dto.getUserType())
                .password(dto.getPassword())
                .name(dto.getName())
                .surName(dto.getSurName())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);
        return UserCreateResponseDto.builder()
                .userType(user.getUserType())
                .name(user.getName())
                .surName(user.getSurName())
                .email(user.getEmail())
                .build();
    }

    public UserCreateResponseDto saveWithDto2(UserCreateRequestDto dto){
        User user = UserMapper.INSTANCE.toUser(dto);
        userRepository.save(user);
        return UserMapper.INSTANCE.toUserCreateResponseDto(user);
    }

    public boolean register(UserCreateRequestDto dto){
        Optional<User> user = userRepository.findOptionalByEmail(dto.getEmail());
        if (user.isPresent()){
            throw new CommentAppException(ErrorType.USER_NOT_FOUND, "Kullanıcı zaten kayıtlı");
        }
        save(UserMapper.INSTANCE.toUser(dto));
        return true;

    }


}
