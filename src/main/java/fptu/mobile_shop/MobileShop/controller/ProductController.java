package fptu.mobile_shop.MobileShop.controller;


import fptu.mobile_shop.MobileShop.dto.ProductDTO;
import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import fptu.mobile_shop.MobileShop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
    }

    @GetMapping("/get-product")
    public ResponseEntity<ResponseDTO> getAllProducts() {
        List<Product> products = productService.getAll();
        List<ProductDTO> productDTOs = products.stream().map(item -> ProductDTO.builder()
                .productId(item.getProductID())
                .productName(item.getProductName())
                .productDetails(item.getProductDetails())
                .productImage(item.getProductImage())
                .price(item.getPrice())
                .CategoryID(item.getCategoryID())
                .stockQuantity(item.getStockQuantity())
                .build()).toList();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(productDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/edit-product")
    public ResponseEntity<ResponseDTO> getProductById(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(productService.getOne(productDTO.getProductId()));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/save-new-product")
    public ResponseEntity<ResponseDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Product> existingProduct = Optional.ofNullable(productService.getProductByProductName(productDTO.getProductName()));
        if (existingProduct.isPresent()) {
            responseDTO.setMessage("Product already exists, do you want to update it?");
            responseDTO.setData(existingProduct.get());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
        } else {
        Product product = productService.parseProductDtoToProduct(productDTO);
        responseDTO.setMessage("Get success");
        responseDTO.setData(productService.save(product));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }
    }

    @PostMapping("/delete-product")
    public ResponseEntity<ResponseDTO> deleteProduct(@RequestParam Integer id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            productService.deleteByProductId(id);
            responseDTO.setMessage("Success");
            responseDTO.setData("Delete success");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Delete failed: " + e.getMessage());
            responseDTO.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping("/submitForm")
    public String submitForm(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (productDTO.getStockQuantity() < 1){
            bindingResult.rejectValue("productDTO","stockQuantity", "Stock quantity must be greater than 0");
        }
        if (bindingResult.hasErrors()) {
            return "Validation failed";
        }
        return "Form submitted successfully";
    }

    @PostMapping("/save-edit-product")
    public ResponseEntity<ResponseDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Product> existingProduct = Optional.ofNullable(productService.getProductByProductName(productDTO.getProductName()));
        if(existingProduct.isEmpty()){
            responseDTO.setMessage("Product does not exist");
        }else{
            Product product = productService.updateProduct(
                    existingProduct.get().getProductID(),
                    productDTO.getProductName(),
                    productDTO.getProductDetails(),
                    productDTO.getProductImage(),
                    productDTO.getPrice(),
                    productDTO.getStockQuantity(),
                    productDTO.getCategoryID());
            responseDTO.setMessage("Update success");
            responseDTO.setData(product);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}