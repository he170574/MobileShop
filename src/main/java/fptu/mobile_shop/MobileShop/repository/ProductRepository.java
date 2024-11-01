package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    void deleteByProductID(Integer id);
    Product findProductByProductName(String productName);
    Product findProductByProductID(Integer id);
    List<Product> findAllByDeletedIsFalse();

    @Query("SELECT p FROM Product p WHERE "
            + "(LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND "
            + "(:category IS NULL OR p.categoryName = :category) AND "
            + "(:minPrice IS NULL OR p.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> findByFilters(
            @Param("searchTerm") String searchTerm,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable);

}
