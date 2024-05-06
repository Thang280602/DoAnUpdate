package com.otothang.controllers.OAuth2;

import com.otothang.Service.UserService;
import com.otothang.customOAuth2.GoogleUserInfoProvider;
import com.otothang.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OAuth2Controller {
    @Autowired
    private GoogleUserInfoProvider googleUserInfoProvider;

    @Autowired
    private UserService userService;

    @GetMapping("/oauth2/authorization/google")
    public String handleGoogleLoginCallback(@RequestParam("code") String code) {
        // Lấy thông tin người dùng từ Google
        User user = googleUserInfoProvider.getGoogleUserInfo(code);

        // Lưu thông tin người dùng vào cơ sở dữ liệu
        userService.processOAuthPostLogin(user.getUserName());

        // Chuyển hướng đến trang index hoặc trang chính của ứng dụng
        return "redirect:/index";
    }
}
