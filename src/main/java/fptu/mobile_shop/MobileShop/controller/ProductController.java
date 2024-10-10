package fptu.mobile_shop.MobileShop.controller;


import fptu.mobile_shop.MobileShop.dto.CategoryDTO;
import fptu.mobile_shop.MobileShop.dto.ProductDTO;
import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.Category;
import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import fptu.mobile_shop.MobileShop.service.CategoryService;
import fptu.mobile_shop.MobileShop.service.CloudinaryService;
import fptu.mobile_shop.MobileShop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductController {
    private final ProductService productService;
    private CategoryService categoryService;

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
                .productImageUrl(item.getProductImage())
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

    @PostMapping(value = "/save-new-product", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> saveProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productPrice") Double price,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("productStock") Integer stockQuantity,
            @RequestParam("imageUpload") MultipartFile productImage) throws IOException { // Nhận MultipartFile cho hình ảnh
        ProductDTO productDTO = ProductDTO.builder()
                .productName(productName)
                .productDetails(productDescription)
                .price(price)
                .CategoryID(categoryId)
                .stockQuantity(stockQuantity)
                .productImage(productImage)
                .build();
        //Upload Image
        CloudinaryService cloudinaryService = new CloudinaryService();
        String imgUrl = cloudinaryService.uploadImage(productDTO.getProductImage());
        productDTO.setProductImageUrl(imgUrl);

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
    public ResponseEntity<ResponseDTO> updateProduct(@RequestParam("productName") String productName,
                                                     @RequestParam("productDescription") String productDescription,
                                                     @RequestParam("productPrice") Double price,
                                                     @RequestParam("categoryId") String categoryId,
                                                     @RequestParam("productStock") Integer stockQuantity,
                                                     @RequestParam(value = "editImageUpload",required = false) MultipartFile productImage) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Product> existingProduct = Optional.ofNullable(productService.getProductByProductName(productName));
        if(existingProduct.isEmpty()){
            responseDTO.setMessage("Product does not exist");
        }else{

            ProductDTO productDTO = ProductDTO.builder()
                    .productName(productName)
                    .productDetails(productDescription)
                    .price(price)
                    .CategoryID(categoryId)
                    .stockQuantity(stockQuantity)
                    .productImage(productImage)
                    .productImageUrl("")
                    .build();
            //nếu người dùng chọn ảnh mới thì upload lại ảnh lên cloudinary
            if(productDTO.getProductImage() != null){
                CloudinaryService cloudinaryService = new CloudinaryService();
                String imgUrl = cloudinaryService.uploadImage(productDTO.getProductImage());
                productDTO.setProductImageUrl(imgUrl);
            }

            Product product = productService.updateProduct(
                    existingProduct.get().getProductID(),
                    productDTO.getProductName(),
                    productDTO.getProductDetails(),
                    productDTO.getProductImageUrl(),
                    productDTO.getPrice(),
                    productDTO.getStockQuantity(),
                    productDTO.getCategoryID());

            responseDTO.setMessage("Update success");
            responseDTO.setData(product);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

//    @GetMapping("/get-all-category")
//    private ResponseDTO getAllCategory() {
//        ResponseDTO responseDTO = new ResponseDTO();
//        List<CategoryDTO> categoryDTOS = new ArrayList<>();
//        for (Category category : CategoryService.getAll()){
//            categoryDTOS.add(CategoryDTO.builder()
//                        .CategoryID(category.getCategoryID())
//                        .CategoryName(category.getCategoryName())
//                        .build());
//        }
//        responseDTO.setData(categoryDTOS);
//        responseDTO.setMessage("Get all category successful");
//        return ResponseEntity.ok().body(responseDTO).getBody();
//    }

    @PostMapping("/admin/product/add-new-category")
    private ResponseEntity<ResponseDTO> addNewCategory(@RequestBody CategoryDTO categoryDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Category category = categoryService.save(categoryService.parseCategoryDtoToCategory(categoryDTO));
        categoryDTO.setCategoryID(category.getCategoryID());
        responseDTO.setData(categoryDTO);
        responseDTO.setMessage("Add new category successful");
        return ResponseEntity.ok().body(responseDTO);
    }





}