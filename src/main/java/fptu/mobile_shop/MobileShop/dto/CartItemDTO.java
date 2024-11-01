package fptu.mobile_shop.MobileShop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private int id;
    private Integer productId;
    private int quantity;
    private String image;
    private String productName;
    private double productPrice;

}
