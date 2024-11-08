package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.FeedbackFilterRequest;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.FeedbackManageResponse;
import fptu.mobile_shop.MobileShop.dto.ProductCommentDTO;
import fptu.mobile_shop.MobileShop.entity.ProductComment;
import org.springframework.data.domain.Page;

public interface FeedbackService {
    Page<ProductCommentDTO> getPageFeedback(FeedbackFilterRequest request);

    Page<FeedbackManageResponse> getListFeedbackManage(FeedbackFilterRequest request);

    ProductComment getProductCommentById(Integer commentId);

    ProductCommentDTO creatProductComment(ProductCommentDTO request);

    ProductCommentDTO updateProductComment(Integer commentId, ProductCommentDTO request);

    void deleteProductComment(Integer commentId);

    FeedbackManageResponse getFeedbackDetail(Integer commentId);
}
