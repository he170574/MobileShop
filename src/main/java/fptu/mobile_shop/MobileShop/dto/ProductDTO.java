package fptu.mobile_shop.MobileShop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProductDTO {

    private Integer productId;

    @NotNull(message = "Name is required")
    private String productName;

    @NotNull(message = "Details is required")
    private String productDetails;

    private MultipartFile productImage;

    private String productImageUrl;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Cost is required")
    private Double cost;

    @NotNull(message = "Brand is required")
    private String categoryName;

    @NotNull(message = "Quantity is required")
    private Integer stockQuantity;

}
