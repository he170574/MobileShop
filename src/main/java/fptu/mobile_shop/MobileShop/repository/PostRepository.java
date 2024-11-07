package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategoryPost_CategoryID(Long categoryId, Pageable pageable);
    Page<Post> findByTitleContaining(String title, Pageable pageable);
}
