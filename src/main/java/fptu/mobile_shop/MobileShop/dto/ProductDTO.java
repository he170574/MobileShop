package fptu.mobile_shop.MobileShop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
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

    @NotNull(message = "Category is required")
    private String CategoryID;

    @NotNull(message = "Quantity is required")
    private Integer stockQuantity;
}
