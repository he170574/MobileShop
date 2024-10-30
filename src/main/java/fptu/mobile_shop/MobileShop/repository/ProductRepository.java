package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.dto.ProductDTO;
import fptu.mobile_shop.MobileShop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    void deleteByProductID(Integer id);
    Product findProductByProductName(String productName);
    Product findProductByProductID(Integer id);
    List<Product> findAllByDeletedIsFalse();
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    @Query("SELECT new fptu.mobile_shop.MobileShop.dto.ProductDTO(p.productID, p.productName, p.productImage, p.price, SUM(oi.stockQuantity)) " +
            "FROM Product p JOIN OrderItems oi ON p.ProductID = oi.ProductID " +
            "GROUP BY p.ProductID, p.ProductName, p.ProductImage, p.price " +
            "ORDER BY SUM(oi.Quantity) DESC")
    List<ProductDTO> findTopSellingProducts(Pageable pageable);

}
