//package com.otothang.controllers.OAuth2;
//
//import com.otothang.Service.UserService;
//import com.otothang.customOAuth2.GoogleUserInfoProvider;
//import com.otothang.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class OAuth2Controller {
//    @Autowired
//    private GoogleUserInfoProvider googleUserInfoProvider;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/oauth2/authorization/google")
//    public String handleGoogleLoginCallback(@RequestParam("code") String code) {
//        User user = googleUserInfoProvider.getGoogleUserInfo(code);
//
//        userService.processOAuthPostLogin(user.getUserName());
//
//        return "redirect:/index";
//    }
//}
