package fptu.mobile_shop.MobileShop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotNull
    private Integer productId;

    @NotNull(message = "Name is required")
    private String productName;

    @NotNull(message = "Detail is required")
    private String productDetails;

    private MultipartFile productImage;

    private String productImageUrl;

    @NotNull(message = "Price is required")
    private Double price;

    private Double cost;

    @NotNull(message = "Category is required")
    private String categoryName;

    @NotNull(message = "Quantity is required")
    private Integer stockQuantity;
}
