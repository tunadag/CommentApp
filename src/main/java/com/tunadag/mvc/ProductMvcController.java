package com.tunadag.mvc;

import com.tunadag.dto.request.ProductCreateRequestDto;
import com.tunadag.repository.entity.Like;
import com.tunadag.repository.entity.Product;
import com.tunadag.repository.entity.User;
import com.tunadag.service.ProductService;
import com.tunadag.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/productmvc")
public class ProductMvcController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/createproduct")
    public ModelAndView getProductPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createproduct");
        return modelAndView;
    }

    @PostMapping("/createproduct")
    public ModelAndView createProduct(ProductCreateRequestDto dto){
        productService.savewithRequest(dto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:createproduct");
        return modelAndView;
    }

    @GetMapping("/getallproducts")
    public ModelAndView getAllProducts(Long userId){
        User user = userService.findById(userId).get();
        List<Long> productId = user.getLikes().stream().map(x -> x.getProduct().getId()).collect(Collectors.toList());
        Like like = new Like();
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", user);
        modelAndView.addObject("pid", productId);
        modelAndView.addObject("like", like);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/getallproducts2")
    public ModelAndView getAllProducts2(Long userId){
        User user = userService.findById(userId).get();
        List<Long> productId = user.getLikes().stream().map(x -> x.getProduct().getId()).collect(Collectors.toList());
        Like like = new Like();
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", user);
        modelAndView.addObject("pid", productId);
        modelAndView.addObject("like", like);
        modelAndView.setViewName("home3");
        return modelAndView;
    }

}
