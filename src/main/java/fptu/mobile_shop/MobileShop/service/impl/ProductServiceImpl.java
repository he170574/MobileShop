package fptu.mobile_shop.MobileShop.service.impl;


import fptu.mobile_shop.MobileShop.dto.ProductDTO;
import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import fptu.mobile_shop.MobileShop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Optional<Product> getOne(Integer id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product parseProductDtoToProduct(ProductDTO productDTO) {
        return Product.builder()
                .productID(productDTO.getProductId())
                .deleted(false)
                .productName(productDTO.getProductName())
                .productDetails(productDTO.getProductDetails())
                .productImage(productDTO.getProductImageUrl()) // Lưu đường dẫn hình ảnh
                .price(productDTO.getPrice())
                .cost(productDTO.getCost())
                .categoryName(productDTO.getCategoryName())
                .stockQuantity(productDTO.getStockQuantity())
                .build();
    }


    @Override
    public void deleteByProductId(Integer id) {
        Product product = productRepository.findProductByProductID(id);
        if (product != null) {
            product.setDeleted(true);
            productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public Product updateProduct(Integer id, String name, String detail, String image, Double price, Integer quantity, String category) {
        Optional<Product> existingPromotion = productRepository.findById(id);
        if (existingPromotion.isPresent()) {
            Product product = existingPromotion.get();
            product.setProductID(id);
            product.setProductName(name);
            product.setProductDetails(detail);
            if (image != null && !image.isEmpty()){
                product.setProductImage(image);
            }
            product.setPrice(price);
            product.setCategoryName(category);
            product.setStockQuantity(quantity);
            return productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public Product getProductByProductName(String productName){
        return productRepository.findProductByProductName(productName);
    }

    @Override
    public Page<Product> searchProducts(String searchTerm, int page, int size) {
        // Đảm bảo chỉ số trang không nhỏ hơn 0
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByProductNameContaining(searchTerm, pageable);
    }

    public List<ProductDTO> getTopSellingProducts() {
        return productRepository.findTopSellingProducts(PageRequest.of(0, 4));
    }

}
