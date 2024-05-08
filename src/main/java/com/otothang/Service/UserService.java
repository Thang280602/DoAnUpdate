package com.otothang.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.otothang.models.Order;
import jakarta.mail.MessagingException;
import org.springframework.data.repository.query.Param;

import com.otothang.models.User;

public interface UserService {
	List<User> getALL();
	User findByUserName(String userName);
	Boolean create(User user);
	User create1(User user);
	void updateUserById(Long userId,String newUsername,  String newEmail, String newPhone, String newAddress);
	void processOAuthPostLogin(String username ,String email);
	void register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException;
	void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
	boolean verify(String verificationCode);
	void sendOrderConfirmationEmail(User user, Order order, String siteURL) throws MessagingException, UnsupportedEncodingException;
}
