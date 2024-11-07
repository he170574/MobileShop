package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.OrderListManageFilterRequest;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.OrderListManageResponse;
import fptu.mobile_shop.MobileShop.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Page<Order> getListOrder(String keyword, String status, int page, int size);

    public List<Order> getAllOrders();
    public Optional<Order> getOrderById(Long id);
    public Order createOrder(Order order);
    public Order updateOrder(Long id, Order orderDetails);
    public void deleteOrder(Long id);

    Page<OrderListManageResponse> getListOrdersManage(OrderListManageFilterRequest request);

    Order saveOrder(Order order);
}
