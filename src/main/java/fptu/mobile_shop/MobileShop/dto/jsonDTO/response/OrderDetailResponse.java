package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import fptu.mobile_shop.MobileShop.entity.OrderDetail;
import fptu.mobile_shop.MobileShop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private Integer quantity;
    private ProductResponse product;
    private String productName;
    private double productAmount;
    private double cost;

    public OrderDetailResponse(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.quantity = orderDetail.getQuantity();
        this.product = orderDetail.getProduct() != null ? new ProductResponse(orderDetail.getProduct()) : null;
        this.productName = orderDetail.getProductName();
        this.productAmount = orderDetail.getProductAmount();
        this.cost = orderDetail.getCost();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProductResponse {
        private Integer productId;
        private String productName;
        private String productImage;

        public ProductResponse(Product product) {
            this.productId = product.getProductID();
            this.productName = product.getProductName();
            this.productImage = product.getProductImage();
        }
    }
}
