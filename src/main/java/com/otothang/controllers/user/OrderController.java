package com.otothang.controllers.user;

import com.otothang.Service.*;
import com.otothang.models.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
	@Autowired
	private CardItemSevice cardItemSevice;
	@Autowired
	private CardSevice cardSevice;
	@Autowired
	private ProductSevice productSevice;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderSevice orderSevice;
	@Autowired
	private InformationShopSevice informationShopSevice;
	@Autowired
	private OrderDetailSevice orderDetailSevice;
	@Autowired
	private CategorySevice categorySevice;
	@Autowired
	private BlogService blogService;
	@RequestMapping("/checkout")
	public String checkout(Principal principal, Model model) {
		if (principal == null) {
			return "/user/login";
		}
		CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Card card = this.cardSevice.findByUser(customUserDetails.getUser());
		model.addAttribute("user", customUserDetails.getUser());
		model.addAttribute("total",card.totalPrice()) ;
		model.addAttribute("listCard", card);
		List<InformationShop> informationShops = informationShopSevice.getAll();
		model.addAttribute("infor",informationShops);
		List<Category> categories1=categorySevice.getAll();
		model.addAttribute("cate1", categories1);
		Order order = new Order();
		order.setUser(customUserDetails.getUser());
		model.addAttribute("order", order);
		List<Blog> blog=this.blogService.getAll();
		model.addAttribute("blog", blog);
		return "/user/checkout";
	}

	@PostMapping("/postCheckout")
	public String postCheckout(Model model, Principal principal, @ModelAttribute("order") Order order , @RequestParam("paymentMethod") String paymentMethod , HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		if (principal == null) {
			return "/user/login";
		}
		CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Card card = this.cardSevice.findByUser(customUserDetails.getUser());
		model.addAttribute("total",card.totalPrice());
		order.setUser(customUserDetails.getUser());
		order.setCreateAt(new Date());
		order.setStatus(0);
		order.setTotalprice(card.totalPrice());
		if (this.orderSevice.create(order)) {
			for (CardItem cardItem : card.getCardItems()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setPrice(cardItem.getProduct().getPrice());
				orderDetail.setProduct(cardItem.getProduct());
				orderDetail.setQuantity(cardItem.getQuantity());
				this.orderDetailSevice.add(orderDetail);

			}
		}
		model.addAttribute("checkstatus",getStatusText(order.getStatus()));
		model.addAttribute("card",card);
		List<Category> categories1=categorySevice.getAll();
		model.addAttribute("cate1", categories1);
		this.cardItemSevice.deleteByCardId(card.getId());
		List<Blog> blog=this.blogService.getAll();
		model.addAttribute("blog", blog);
		User user = customUserDetails.getUser();
		userService.sendOrderConfirmationEmail(user,order, getSiteURL(request));
		if (paymentMethod.equals("online")) {
			return "redirect:/pay/" + order.getId();
		} else {
			return "user/Build";
		}
	}
	public String getStatusText(int statusCode) {
		switch (statusCode) {
			case 0:
				return "Chờ xác nhận";
			case 1:
				return "Đã duyệt";
			case 2:
				return "Đang giao";
			case 3:
				return "Giao hàng thành công";
			default:
				return "Unknown";
		}
	}
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

}
