package com.otothang.controllers.user;

import java.io.UnsupportedEncodingException;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otothang.Service.BlogService;
import com.otothang.Service.CategorySevice;
import com.otothang.Service.UserService;
import com.otothang.models.Blog;
import com.otothang.models.Category;
import com.otothang.models.User;

@Controller
public class UserLoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private CategorySevice categorySevice;
	@Autowired
	private BlogService blogService;
	@RequestMapping("/login")
	public String login( Model model) {
		List<Category> categories=categorySevice.getAll();
		model.addAttribute("listCate", categories);
		List<Blog> blog=this.blogService.getAll();
		model.addAttribute("blog", blog);
		return "/user/login";
	}
	@RequestMapping("/register")
	public String register(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		List<Category> categories=categorySevice.getAll();
		model.addAttribute("listCate", categories);
		List<Blog> blog=this.blogService.getAll();
		model.addAttribute("blog", blog);
		return "/user/register";
	}
	@PostMapping("/register")
	public String doRegister(@ModelAttribute("user") User user,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		userService.register(user, getSiteURL(request));
		return "/user/Login";
	}
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code ,Model model) {

		if (userService.verify(code)) {
			return "redirect:/register";
		} else {
			return "/user/Login";
		}
	}
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
