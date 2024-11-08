package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Lấy các bài viết có statusPost = true
    Page<Post> findByStatusPostTrue(Pageable pageable);

    // Lấy bài viết theo categoryId và statusPost = true
    Page<Post> findByCategoryPost_CategoryIDAndStatusPostTrue(Long categoryId, Pageable pageable);

    // Tìm bài viết theo title và statusPost = true
    Page<Post> findByTitleContainingAndStatusPostTrue(String title, Pageable pageable);

    // Tìm bài viết theo categoryId, title và statusPost = true
    Page<Post> findByCategoryPost_CategoryIDAndTitleContainingIgnoreCaseAndStatusPostTrue(Long categoryId, String title, Pageable pageable);
}
