package com.otothang.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import com.otothang.models.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.otothang.Repository.Repository;
import com.otothang.Repository.UserRepository;
import com.otothang.models.User;

@Service
public class UserServiceIpml implements UserService {
    @Autowired
    private Repository userRepository;
    @Autowired
    private UserRepository userRepository1;
    @Autowired
    private UserRepository repository;



    @Autowired
    private JavaMailSender mailSender;
    @Override
    public User findByUserName(String userName) {

        return userRepository.findByUserName(userName);
    }

    @Override
    public Boolean create(User user) {
        try {
            this.userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User create1(User user) {
        // TODO Auto-generated method stub
        return this.create1(user);
    }

    @Override
    public void updateUserById(Long userId, String newUsername, String newEmail, String newPhone,
                               String newAddress) {
        this.repository.updateUserById(userId, newUsername, newEmail, newPhone, newAddress);

    }

    @Override
    public void processOAuthPostLogin(String username ,String email) {
        User existUser = userRepository1.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(username);
            newUser.setUserName(email);
            newUser.setProvider(User.Provider.GOOGLE);
            newUser.setEnabled(true);
            userRepository1.save(newUser);
        }
    }


    @Override
    public void register(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassWord());
        user.setPassWord(encodedPassword);

        String randomCode = UUID.randomUUID().toString();
        user.setVerificationCode(randomCode);
        user.setEnabled(true);

        userRepository.save(user);

        sendVerificationEmail(user, siteURL);
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "thang.danghuu@vti.com.vn";
        String senderName = "Confirm when creating account";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Đặng Hữu Thắng";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
    @Override
    public void sendOrderConfirmationEmail(User user, Order order, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "thang.danghuu@vti.com.vn";
        String senderName = "Order Confirmation";
        String subject = "Order Confirmation - Order #" + order.getId();
        String content = "Dear [[name]],<br>"
                + "Thank you for your order. Your order has been successfully placed.<br>"
                + "Order Details:<br>"
                + "<ul>"
                + "<li>Order Number: " + order.getId() + "</li>"
                + "<li>Order Date: " + order.getCreateAt() + "</li>"
                + "<li>Order Address: " + order.getAddressShip() + "</li>"
                + "<li>Order Telephone: " + order.getPhone() + "</li>"
                + "<li> Total:  " + order.getTotalprice() + " $ " + "</li>"
                + "</ul>"
                + "For further inquiries or assistance, please contact us.<br>"
                + "Thank you for choosing us,<br>"
                + "Đặng Hữu Thắng.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.getEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }


    @Override
    public List<User> getALL() {

        return this.userRepository.findAll();
    }


}
