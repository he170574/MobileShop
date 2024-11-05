package fptu.mobile_shop.MobileShop.dto;

import fptu.mobile_shop.MobileShop.entity.ProductComment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentDTO {
    private Integer commentId;
    @NotNull()
    private Integer productId;
    private Integer ordersId;
    @NotNull()
    private Integer accountId;
    private String commentText;
    private LocalDateTime commentDate;

    public ProductCommentDTO(ProductComment productComment) {
        this.commentId = productComment.getCommentId();
        this.productId = productComment.getProduct() != null ? productComment.getProduct().getProductID() : null;
        this.accountId = productComment.getAccount() != null ? productComment.getAccount().getAccountId() : null;
        this.commentText = productComment.getCommentText();
        this.commentDate = productComment.getCommentDate();
    }
}
