package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    void deleteByProductID(Integer id);
    Product findProductByProductName(String productName);
    Product findProductByProductID(Integer id);
    List<Product> findAllByDeletedIsFalse();
}
