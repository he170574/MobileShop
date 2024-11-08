package fptu.mobile_shop.MobileShop.dto.jsonDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest {
    private Long id;
    private String status;
}
