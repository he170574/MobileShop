package fptu.mobile_shop.MobileShop.service;



import fptu.mobile_shop.MobileShop.dto.ProductDTO;
import fptu.mobile_shop.MobileShop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAll();

    Optional<Product> getOne(Integer id);

    Product save(Product product);

    Product parseProductDtoToProduct(ProductDTO productDTO);

    void deleteByProductId(Integer id);

    Product updateProduct(Integer id, String name, String detail, String image, Double price, Integer quantity, String category);

    Product getProductByProductName(String name);
}
