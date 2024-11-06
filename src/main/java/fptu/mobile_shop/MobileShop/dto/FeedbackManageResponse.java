package fptu.mobile_shop.MobileShop.dto;

import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.entity.ProductComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackManageResponse {
    private Integer commentId;
    private String commentText;
    private LocalDateTime commentDate;
    private AccountResponse account;
    private ProductResponse product;
    private OrdersResponse orders;

    public FeedbackManageResponse(ProductComment productComment) {
        this.commentId = productComment.getCommentId();
        this.commentText = productComment.getCommentText();
        this.commentDate = productComment.getCommentDate();
        this.account = productComment.getAccount() != null ? new AccountResponse(productComment.getAccount()) : null;
        this.product = productComment.getProduct() != null ? new ProductResponse(productComment.getProduct()) : null;
        this.orders = productComment.getOrder() != null ? new OrdersResponse(productComment.getOrder()) : null;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProductResponse {
        private Integer productId;
        private String productName;

        public ProductResponse(Product product) {
            this.productId = product.getProductID();
            this.productName = product.getProductName();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class OrdersResponse {
        private Integer ordersId;
        private String ordersCode;

        public OrdersResponse(Order order) {
            this.ordersId = Math.toIntExact(order.getId());
//            this.ordersCode = order.getOrderCode();
        }
    }
}
