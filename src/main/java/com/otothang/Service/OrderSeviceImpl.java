package com.otothang.Service;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otothang.Repository.OrderRepository;
import com.otothang.models.Order;
@Transactional
@Service
public class OrderSeviceImpl implements OrderSevice{
	@Autowired
	private OrderRepository orderRepository;
	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return this.orderRepository.findAll();
	}

	@Override
	public Boolean create(Order Order) {
		try {
			this.orderRepository.save(Order);
			return true;
		} catch (Exception e) {
			e.printStackTrace(); // Log lỗi
			throw new RuntimeException("Failed to create order: " + e.getMessage()); // Ném ra ngoại lệ với thông báo lỗi
		}
	}

	@Override
	public Order findById(Integer id) {
		// TODO Auto-generated method stub
		return this.orderRepository.findById(id).get();
	}


}
