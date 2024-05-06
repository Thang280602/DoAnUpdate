package com.otothang.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void processOAuthPostLogin(String username) {
		User existUser = userRepository1.getUserByUsername(username);

		if (existUser == null) {
			User newUser = new User();
			newUser.setUserName(username);
			newUser.setProvider(User.Provider.GOOGLE);
			newUser.setEnabled(true);
			userRepository1.save(newUser);
		}
	}

	@Override
	public List<User> getALL() {
		
		return this.userRepository.findAll();
	}
	
}
