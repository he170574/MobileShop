package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
