package fptu.mobile_shop.MobileShop.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.OrderListManageFilterRequest;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.OrderListManageResponse;
import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.entity.Role;
import fptu.mobile_shop.MobileShop.final_attribute.STATUS;
import fptu.mobile_shop.MobileShop.repository.AccountRepository;
import fptu.mobile_shop.MobileShop.repository.OrderRepository;
import fptu.mobile_shop.MobileShop.repository.RoleRepository;
import fptu.mobile_shop.MobileShop.security.CustomAccount;
import fptu.mobile_shop.MobileShop.service.OrderService;
import fptu.mobile_shop.MobileShop.service.RoleService;
import fptu.mobile_shop.MobileShop.util.CommonPage;
import fptu.mobile_shop.MobileShop.util.CommonUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hibernate.engine.transaction.internal.jta.JtaStatusHelper.getStatus;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Page<Order> getListOrder(String keyword, String status, int page, int size) {
        // Get current user login
        String login = CustomAccount.getCurrentUsername();
        Optional<Account> accountOpl = accountRepository.findByUsername(login);
        if (accountOpl.isPresent()) {
            this.changeStatusOrder(accountOpl.get());
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        if(StringUtils.endsWithIgnoreCase(accountOpl.get().getRole().getRoleName(), "ROLE_ADMIN") || StringUtils.endsWithIgnoreCase(accountOpl.get().getRole().getRoleName(), "ROLE_STAFF")){
            return orderRepository.getListAllOrder(pageable);
        }
        return orderRepository.getListAllOrderUser(pageable, accountOpl.get().getAccountId());
    }

    @Transactional
    public void changeStatusOrder(Account account) {
        if (CommonUtil.isEmpty(account)) return;
        Long userId = null;
        userId = Long.valueOf(account.getAccountId());
        List<Order> listOrder = new ArrayList<>();
        listOrder = orderRepository.getAllOrderByUserId(userId);
        for (Order order : listOrder) {
            String code = order.getShippingCode();
            String url = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail";
            String token = "3b87f02d-415f-11ef-8e53-0a00184fe694";
            String requestBody = "{\"order_code\": \"" + code + "\"}";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Token", token)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == HttpStatus.OK.value()) {
                    String statusGhn = getStatus(response.body());
                    if (Objects.equals(statusGhn, "cancel")) {
                        order.setOrderStatus(STATUS.CANCEL);
                    } else if (Objects.equals(statusGhn, "delivered")) {
                        order.setOrderStatus(STATUS.DELIVERY_SUCCESS);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (CommonUtil.isNotEmpty(listOrder)) {
            orderRepository.saveAll(listOrder);
        }
    }

    public static String getStatus(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null && dataNode.has("status")) {
                return dataNode.get("status").asText();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

}
