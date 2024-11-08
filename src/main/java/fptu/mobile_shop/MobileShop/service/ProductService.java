package fptu.mobile_shop.MobileShop.service;



import fptu.mobile_shop.MobileShop.dto.ProductDTO;
import fptu.mobile_shop.MobileShop.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getById(Integer id);

    List<Product> getAll();

    Optional<Product> getOne(Integer id);

    Product save(Product product);

    Product parseProductDtoToProduct(ProductDTO productDTO);

    void deleteByProductId(Integer id);

    Product updateProduct(Integer id, String name, String detail, String image, Double price, Integer quantity, String category);

    Product getProductByProductName(String name);

    List<Product> filterProducts(String searchTerm, Double minPrice, Double maxPrice, String sort, String category);

    Page<Product> filterProductsWithPagination(String searchTerm, Double minPrice, Double maxPrice, String sort, String category, int page, int size);

}
