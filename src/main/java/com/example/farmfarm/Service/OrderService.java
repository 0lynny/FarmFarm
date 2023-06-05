package com.example.farmfarm.Service;

import com.example.farmfarm.Entity.OrderEntity;
import com.example.farmfarm.Entity.UserEntity;
import com.example.farmfarm.Repository.OrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderEntity createOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public OrderEntity getOrder(long oId) {
        return orderRepository.findByoId(oId);
    }

    public List<OrderEntity> myOrderList(UserEntity user) {
        return orderRepository.findByUser(user);
    }


}
