package com.tunadag.mvc;

import com.tunadag.dto.request.LoginRequestDto;
import com.tunadag.dto.request.UserCreateRequestDto;
import com.tunadag.repository.entity.EUserType;
import com.tunadag.repository.entity.User;
import com.tunadag.service.ProductService;
import com.tunadag.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LoginMvcController {

    private final UserService userService;
    private final ProductService productService;
    private final ProductMvcController productMvcController;

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/dologin")
    public ModelAndView doLogin(LoginRequestDto dto){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> user = userService.login(dto.getEmail(), dto.getPassword());
        boolean isUser = true;
        boolean isProduct = true;
        if (user.isPresent()){
            if (user.get().getUserType().equals(EUserType.ADMINISTRATOR)){
                return users();
//                return admin(); //postmappingle admin metodunu çağırıyoruz
            } else {
                modelAndView.addObject("userId", user.get().getId());
                modelAndView.setViewName("redirect:/productmvc/getallproducts");
//                return productMvcController.getAllProducts(user.get().getId());
            }
        } else {
            modelAndView.setViewName("redirect:login");
        }
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView users(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userlist", userService.findAll());
        modelAndView.addObject("isproducts", false);
        modelAndView.addObject("isuser", true);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @PostMapping("/products")
    public ModelAndView postproducts(Boolean isUser, Boolean isProduct){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isproducts", isProduct);
        modelAndView.addObject("isuser", isUser);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/products")
    public ModelAndView products(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", productService.findAll());
        modelAndView.addObject("isproducts", true);
        modelAndView.addObject("isuser", false);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("userType", EUserType.values());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView index(UserCreateRequestDto dto){
        boolean isregister = userService.register(dto);
        ModelAndView model = new ModelAndView();
        if (isregister){
            model.setViewName("redirect:/login");
        } else {
            model.addObject("error",
                    "Kullanıcı daha önce oluşturulmuştur");
            model.setViewName("user/register");
        }
        return model;
    }
}
