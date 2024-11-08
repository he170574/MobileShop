package fptu.mobile_shop.MobileShop.dto.jsonDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackFilterRequest {
    private Integer productId;
    private Integer accountId;
    private LocalDateTime commentDate;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
}
