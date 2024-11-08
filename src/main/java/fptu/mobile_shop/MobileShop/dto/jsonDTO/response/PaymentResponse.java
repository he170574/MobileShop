package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.DataPayment;
import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Getter
@Setter
public class PaymentResponse {
    private int error;
    private String message;
    private DataPayment data;
}
