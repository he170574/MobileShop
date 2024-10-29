package fptu.mobile_shop.MobileShop.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private Object data;
    private int totalPages;
    private int currentPage;
}
