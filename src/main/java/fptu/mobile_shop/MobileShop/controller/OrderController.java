package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.OrderListManageFilterRequest;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.OrderListManageResponse;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list-manage")
    public ResponseEntity<ResponseDTO> getListOrdersManage(@Validated OrderListManageFilterRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        Page<OrderListManageResponse> response = orderService.getListOrdersManage(request);
        responseDTO.setData(response.getContent());
        if (response.getContent().size() != 0) {
            responseDTO.setCurrentPage(response.getNumber() + 1);
        }
        responseDTO.setTotalPages(response.getTotalPages());
        responseDTO.setMessage("Success");
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ResponseDTO> getOrderDetailById(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()) {
            responseDTO.setData(new OrderListManageResponse(orderOpt.get()));
            responseDTO.setMessage("Success");
            return ResponseEntity.ok().body(responseDTO);
        } else {
            responseDTO.setMessage("Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);  // Trả về mã HTTP 404 Not Found
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
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
