package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.dto.FeedbackFilterRequest;
import fptu.mobile_shop.MobileShop.dto.FeedbackManageResponse;
import fptu.mobile_shop.MobileShop.dto.ProductCommentDTO;
import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.entity.ProductComment;
import fptu.mobile_shop.MobileShop.repository.ProductCommentRepository;
import fptu.mobile_shop.MobileShop.service.AccountService;
import fptu.mobile_shop.MobileShop.service.FeedbackService;
import fptu.mobile_shop.MobileShop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final ProductCommentRepository productCommentRepository;
    private final AccountService accountService;
    private final ProductService productService;

    @Transactional
    @Override
    public ProductComment getProductCommentById(Integer commentId) {
        ProductComment productComment = productCommentRepository.getProductCommentById(commentId);
        return productComment;
    }

    @Override
    @Transactional
    public Page<ProductCommentDTO> getPageFeedback(FeedbackFilterRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "commentDate");
        Pageable pageable = this.pageWithSort(request.getPageNum(), request.getPageSize(), sort);
        Page<ProductComment> pageFeedback = productCommentRepository.getListFeedbackPage(request, pageable);
        if (pageFeedback.getContent() == null || pageFeedback.getContent().size() == 0) return Page.empty(pageable);
        return pageFeedback.map(ProductCommentDTO::new);
    }

    @Override
    @Transactional
    public Page<FeedbackManageResponse> getListFeedbackManage(FeedbackFilterRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "commentDate");
        Pageable pageable = this.pageWithSort(request.getPageNum(), request.getPageSize(), sort);
        Page<ProductComment> pageFeedback = productCommentRepository.getListFeedbackPage(request, pageable);
        if (pageFeedback.getContent() == null || pageFeedback.getContent().size() == 0) return Page.empty(pageable);
        return pageFeedback.map(FeedbackManageResponse::new);
    }

    @Transactional
    @Override
    public ProductCommentDTO creatProductComment(ProductCommentDTO request) {
        Product product = request.getProductId() != null ? productService.getById(request.getProductId()).get() : null;
        Account account = request.getAccountId() != null ? accountService.getAccountByAccountId(request.getAccountId()) : null;
        ProductComment entity = new ProductComment();
        entity.setCommentText(request.getCommentText());
        entity.setCommentDate(LocalDateTime.now());
        // Ánh xạ các đối tượng liên kết
        entity.setProduct(product);
        entity.setAccount(account);
        ProductComment productComment = productCommentRepository.save(entity);
        return new ProductCommentDTO(productComment);
    }

    @Transactional
    @Override
    public ProductCommentDTO updateProductComment(Integer commentId, ProductCommentDTO request) {
        Product product = request.getProductId() != null ? productService.getById(request.getProductId()).get() : null;
        Account account = request.getAccountId() != null ? accountService.getAccountByAccountId(request.getAccountId()) : null;
        ProductComment entity = this.getProductCommentById(commentId);
        entity.setCommentText(request.getCommentText());
//        entity.setCommentDate(LocalDateTime.now());
        // Ánh xạ các đối tượng liên kết
        entity.setProduct(product);
        entity.setAccount(account);
        ProductComment productComment = productCommentRepository.save(entity);
        return new ProductCommentDTO(productComment);
    }

    @Transactional
    @Override
    public void deleteProductComment(Integer commentId) {
        ProductComment entity = this.getProductCommentById(commentId);
        entity.setIsDeleted(true);
        productCommentRepository.save(entity);
    }

    @Override
    public FeedbackManageResponse getFeedbackDetail(Integer commentId) {
        ProductComment entity = this.getProductCommentById(commentId);
        return new FeedbackManageResponse(entity);
    }

    public Pageable pageWithSort(Integer pageNum, Integer pageSize, Sort sort) {
        int offset = Optional.of(pageNum).map(p -> {
            if (p > 0) return p - 1;
            else return 0;
        }).orElse(0);
        return PageRequest.of(offset, pageSize, sort);
    }
}
