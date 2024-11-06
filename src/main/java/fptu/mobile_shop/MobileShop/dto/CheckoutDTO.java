package fptu.mobile_shop.MobileShop.dto;

import fptu.mobile_shop.MobileShop.entity.Cart;
import fptu.mobile_shop.MobileShop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckoutDTO {
    private Order order;
    private Cart cart;
    private double shippingFee;
    private double totalAmount;
    private InvoiceDto invoice;  // Thêm đối tượng Invoice

    public CheckoutDTO(Order order, Cart cart, double shippingFee, double totalAmount) {
        this.order = order;
        this.cart = cart;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
    }
}
