package fptu.mobile_shop.MobileShop.dto.jsonDTO;

import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Getter
@Setter
public class TransactionDTO {
    public int error;
    public String message;
    public Data data;
}
