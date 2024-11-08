package fptu.mobile_shop.MobileShop.dto.jsonDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListManageFilterRequest {
    private String keyword;
    private String orderStatus;
    private Integer accountId;

    private String sortBy;
    private String orderBy;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
}
