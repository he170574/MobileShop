package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.OrderListManageFilterRequest;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.OrderListManageResponse;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.repository.OrderRepository;
import fptu.mobile_shop.MobileShop.service.OrderService;
import fptu.mobile_shop.MobileShop.util.CommonPage;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setOrderDate(orderDetails.getOrderDate());
            order.setAccount(orderDetails.getAccount());
            order.setOrderStatus(orderDetails.getOrderStatus());
            order.setTotalAmount(orderDetails.getTotalAmount());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<OrderListManageResponse> getListOrdersManage(OrderListManageFilterRequest request) {
        // Xác định sort direction
        Sort.Direction direction = request.getOrderBy().equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, request.getSortBy());
        // Tạo Pageable với sort đã xác định
        Pageable pageable = CommonPage.pageWithSort(request.getPageNum(), request.getPageSize(), sort);
        // Lấy danh sách đơn hàng từ repository
        Page<Order> pageOrder = orderRepository.getListOrdersManage(request, pageable);
        // Nếu không có đơn hàng nào, trả về Page.empty()
        if (pageOrder.getContent().isEmpty()) {
            return Page.empty(pageable);
        }
        // Chuyển đổi Page<Order> sang Page<OrderListManageResponse>
        return pageOrder.map(OrderListManageResponse::new);
    }

}
