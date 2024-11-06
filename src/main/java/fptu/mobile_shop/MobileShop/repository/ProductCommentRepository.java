package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.FeedbackFilterRequest;
import fptu.mobile_shop.MobileShop.entity.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {
    @Query("""
            select x1 from ProductComment x1 where x1.commentId = :commentId
            """)
    ProductComment getProductCommentById(Integer commentId);

    @Query("""
            select x1 from ProductComment x1
            left join x1.account x2
            left join x1.product x3
            where x1.isDeleted = false
            and ( :#{#request.accountId} is null or x2.accountId = :#{#request.accountId})
            and ( :#{#request.productId} is null or x3.productID = :#{#request.productId})
                        """)
    Page<ProductComment> getListFeedbackPage(FeedbackFilterRequest request, Pageable pageable);


    @Modifying
    @Query("DELETE FROM ProductComment x1 WHERE x1.commentId = :commentId")
    void deleteProductCommentById(Integer commentId);

}
