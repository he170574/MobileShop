package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public List<Order> getAllOrders();
    public Optional<Order> getOrderById(Long id);
    public Order createOrder(Order order);
    public Order updateOrder(Long id, Order orderDetails);
    public void deleteOrder(Long id);
}
