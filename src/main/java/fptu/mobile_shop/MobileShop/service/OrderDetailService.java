package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    public List<OrderDetail> getAllOrderDetails();
    public Optional<OrderDetail> getOrderDetailById(Long id);
    public OrderDetail createOrderDetail(OrderDetail orderDetail);
    public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetailDetails);
    public void deleteOrderDetail(Long id);
}
