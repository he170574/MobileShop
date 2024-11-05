package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.entity.OrderDetail;
import fptu.mobile_shop.MobileShop.repository.OrderDetailRepository;
import fptu.mobile_shop.MobileShop.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetailDetails) {
        return orderDetailRepository.findById(id).map(orderDetail -> {
            orderDetail.setQuantity(orderDetailDetails.getQuantity());
            orderDetail.setProduct(orderDetailDetails.getProduct());
            orderDetail.setProductName(orderDetailDetails.getProductName());
            orderDetail.setColor(orderDetailDetails.getColor());
            orderDetail.setSize(orderDetailDetails.getSize());
            orderDetail.setProductAmount(orderDetailDetails.getProductAmount());
            orderDetail.setCost(orderDetailDetails.getCost());
            return orderDetailRepository.save(orderDetail);
        }).orElseThrow(() -> new RuntimeException("OrderDetail not found with id " + id));
    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
