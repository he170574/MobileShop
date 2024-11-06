package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListManageResponse {
    private Long id;
    private LocalDateTime orderDate;
    private AccountResponse account;
    private String orderStatus;
    private String orderCode;
    private BigDecimal shippingFee;
    private double totalAmount;

    public OrderListManageResponse(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.account = order.getAccount() != null ? new AccountResponse(order.getAccount()) : null;
        this.orderStatus = order.getOrderStatus();
        this.orderCode = order.getOrderCode();
        this.shippingFee = order.getShippingFee();
        this.totalAmount = order.getTotalAmount();
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
