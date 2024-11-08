package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderCode;
    private LocalDateTime orderDate;
    private AccountResponse account;
    private String orderStatus;
    private String expectedDeliveryTime;
    private BigDecimal shippingFee;
    private String shippingCode;
    private double totalAmount;
    private List<OrderDetailResponse> orderDetails;


    public OrderResponse(Order order) {
        this.id = order.getId();
        this.orderCode = order.getOrderCode();
        this.orderDate = order.getOrderDate();
        this.account = new AccountResponse(order.getAccount());
        this.orderStatus = order.getOrderStatus();
        this.expectedDeliveryTime = order.getExpectedDeliveryTime();
        this.shippingFee = order.getShippingFee();
        this.shippingCode = order.getShippingCode();
        this.totalAmount = order.getTotalAmount();
        this.orderDetails = order.getOrderDetails().stream().map(OrderDetailResponse::new).collect(Collectors.toList());

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AccountResponse {
        private Integer accountId;
        private String fullName;

        public AccountResponse(Account account) {
            this.accountId = account.getAccountId();
            this.fullName = account.getFullName();
        }
    }
}
