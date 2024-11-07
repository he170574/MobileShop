package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
    List<BlogCategory> findByStatusTrue(); // Lấy tất cả category có status = TRUE
}
