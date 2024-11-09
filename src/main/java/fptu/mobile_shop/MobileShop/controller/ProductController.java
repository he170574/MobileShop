package fptu.mobile_shop.MobileShop.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
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
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository,
            CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/get-product")
    public ResponseEntity<ResponseDTO> getProducts(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(required = false) String category) {

        // Gọi service để lấy danh sách sản phẩm đã lọc
        List<Product> products = productService.filterProducts(search, minPrice, maxPrice, sort, category);

        // Chuyển đổi sang DTO
        List<ProductDTO> productDTOs = products.stream().map(item -> ProductDTO.builder()
                .productId(item.getProductID())
                .productName(item.getProductName())
                .productDetails(item.getProductDetails())
                .productImageUrl(item.getProductImage())
                .price(item.getPrice())
                .cost(item.getCost())
                .categoryName(item.getCategoryName())
                .stockQuantity(item.getStockQuantity())
                .build()).toList();

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(productDTOs);
        responseDTO.setTotalPages(1); // Mặc định là 1 trang, có thể thêm phân trang sau
        responseDTO.setCurrentPage(1);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @GetMapping("/get-all-product")
    public ResponseEntity<ResponseDTO> getAllProducts(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Page<Product> productPage = productService.filterProductsWithPagination(search, minPrice, maxPrice, sort, category, page, size);

        List<ProductDTO> productDTOs = productPage.getContent().stream().map(item -> ProductDTO.builder()
                .productId(item.getProductID())
                .productName(item.getProductName())
                .productDetails(item.getProductDetails())
                .productImageUrl(item.getProductImage())
                .price(item.getPrice())
                .cost(item.getCost())
                .categoryName(item.getCategoryName())
                .stockQuantity(item.getStockQuantity())
                .build()).toList();

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(productDTOs);
        responseDTO.setTotalPages(productPage.getTotalPages());
        responseDTO.setCurrentPage(page + 1);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @PostMapping("/edit-product")
    public ResponseEntity<ResponseDTO> getProductById(@Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(productService.getOne(productDTO.getProductId()));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping(value = "/save-new-product", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> saveProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productCost") Double cost,
            @RequestParam("productPrice") Double price,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("productStock") Integer stockQuantity,
            @RequestParam("imageUpload") MultipartFile productImage) throws IOException { // Nhận MultipartFile cho hình
                                                                                          // ảnh
        ProductDTO productDTO = ProductDTO.builder()
                .productName(productName)
                .productDetails(productDescription)
                .cost(cost)
                .price(price)
                .categoryName(categoryName)
                .stockQuantity(stockQuantity)
                .productImage(productImage)
                .build();
        // Upload Image
        CloudinaryService cloudinaryService = new CloudinaryService();
        String imgUrl = cloudinaryService.uploadImage(productDTO.getProductImage());
        productDTO.setProductImageUrl(imgUrl);

        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Product> existingProduct = Optional
                .ofNullable(productService.getProductByProductName(productDTO.getProductName()));
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
        if (productDTO.getStockQuantity() < 1) {
            bindingResult.rejectValue("productDTO", "stockQuantity", "Stock quantity must be greater than 0");
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
            @RequestParam("categoryName") String categoryName,
            @RequestParam("productStock") Integer stockQuantity,
            @RequestParam(value = "editImageUpload", required = false) MultipartFile productImage) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Product> existingProduct = Optional.ofNullable(productService.getProductByProductName(productName));
        if (existingProduct.isEmpty()) {
            responseDTO.setMessage("Product does not exist");
        } else {

            ProductDTO productDTO = ProductDTO.builder()
                    .productName(productName)
                    .productDetails(productDescription)
                    .price(price)
                    .categoryName(categoryName)
                    .stockQuantity(stockQuantity)
                    .productImage(productImage)
                    .productImageUrl("")
                    .build();
            // nếu người dùng chọn ảnh mới thì upload lại ảnh lên cloudinary
            if (productDTO.getProductImage() != null) {
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
                    productDTO.getCategoryName());

            responseDTO.setMessage("Update success");
            responseDTO.setData(product);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get-category")
    private ResponseDTO getAllCategory() {
        ResponseDTO responseDTO = new ResponseDTO();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categoryService.getAll()) {
            categoryDTOS.add(CategoryDTO.builder()
                    .CategoryID(category.getCategoryID())
                    .CategoryName(category.getCategoryName())
                    .build());
        }
        responseDTO.setData(categoryDTOS);
        responseDTO.setMessage("Get all category successful");
        return ResponseEntity.ok().body(responseDTO).getBody();
    }

    @PostMapping("/add-new-category")
    private ResponseEntity<ResponseDTO> addNewCategory(@RequestBody CategoryDTO categoryDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Category category = categoryService.save(categoryService.parseCategoryDtoToCategory(categoryDTO));
        categoryDTO.setCategoryID(category.getCategoryID());
        responseDTO.setData(categoryDTO);
        responseDTO.setMessage("Add new category successful");
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/get-product-detail")
    public ResponseEntity<Product> getProductById(@RequestParam Integer id) {
        Optional<Product> product = productService.getById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/productDetail")
    public String showProductDetail(@RequestParam Integer id, Model model) {
        Optional<Product> product = productService.getById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productDetail"; // Trả về view productDetail.html
        } else {
            return "404"; // Hoặc trang lỗi nếu không tìm thấy sản phẩm
        }
    }


}
