package com.otothang.Service;

import java.util.List;

import com.otothang.models.Order;
import com.otothang.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Transactional
public interface OrderSevice {
	List<Order> getAll();
	Boolean create(Order Order);
	Order findById(Integer id);
}
