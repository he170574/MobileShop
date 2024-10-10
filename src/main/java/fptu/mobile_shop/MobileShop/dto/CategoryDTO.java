package fptu.mobile_shop.MobileShop.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer CategoryID;

    @NotNull(message = "CategoryName is required")
    private String CategoryName;

    private Boolean DELETED;
}
