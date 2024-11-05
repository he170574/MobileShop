package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public  Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        return orderService.updateOrder(id, orderDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("autoPayment")
    public ResponseEntity<ResponseDTO> autoPayment(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        return ResponseEntity.internalServerError().body(responseDTO);
    }
}
