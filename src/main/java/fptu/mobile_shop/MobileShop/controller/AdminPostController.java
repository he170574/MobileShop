package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.AccountDTO;
import fptu.mobile_shop.MobileShop.dto.PostDTO;
import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.BlogCategory;
import fptu.mobile_shop.MobileShop.entity.Post;
import fptu.mobile_shop.MobileShop.security.CustomAccount;
import fptu.mobile_shop.MobileShop.service.AccountService;
import fptu.mobile_shop.MobileShop.service.BlogCategoryService;
import fptu.mobile_shop.MobileShop.service.PostService;
import fptu.mobile_shop.MobileShop.service.CloudinaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/blog")
public class AdminPostController {

    private final PostService postService;
    private final CloudinaryService cloudinaryService;
    private final BlogCategoryService categoryService;
    private final AccountService accountService;

    @Autowired
    public AdminPostController(PostService postService, CloudinaryService cloudinaryService,
                               BlogCategoryService categoryService, AccountService accountService) {
        this.postService = postService;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    // 1. Xem danh sách tất cả các bài viết, tìm kiếm theo tiêu đề hoặc lọc theo danh mục
    @GetMapping
    public String getAllPosts(Model model, @PageableDefault(size = 10) Pageable pageable,
                              @RequestParam(required = false) String query,
                              @RequestParam(required = false) Long category) {
        Page<Post> postsPage;

        // Nếu cả category và query đều có
        if (category != null && query != null && !query.isEmpty()) {
            postsPage = postService.searchByCategoryAndTitle(category, query, pageable);
        }
        // Nếu chỉ có category
        else if (category != null) {
            postsPage = postService.getPostsByCategoryId(category, pageable);
        }
        // Nếu chỉ có query
        else if (query != null && !query.isEmpty()) {
            postsPage = postService.searchByTitle(query, pageable);
        }
        // Nếu không có cả category và query, lấy tất cả bài viết
        else {
            postsPage = postService.getAllPosts(pageable);
        }

        // Thêm các dữ liệu vào model
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", postsPage.getNumber());
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("categoryID", category);
        model.addAttribute("categories", categoryService.getAllActiveCategories());  // Đảm bảo rằng danh sách danh mục được gửi đi
        return "manageBlog";
    }




    // 2. Tạo mới một bài viết (hiển thị form)
    @GetMapping("/admin/blog/new")
    public String showCreateBlogForm(Model model) {
        // Lấy thông tin người dùng từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu người dùng đã đăng nhập
        if (authentication != null) {
            CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
            String currentUsername = customAccount.getUsername();  // Lấy tên người dùng hiện tại

            // Lấy accountId từ tài khoản người dùng đang đăng nhập
            Account currentAccount = accountService.getByUsername(currentUsername);
            if (currentAccount != null) {
                Integer accountId = currentAccount.getAccountId();  // accountId là kiểu Integer

                // Truyền accountId vào model để sử dụng trong form
                model.addAttribute("accountId", accountId);
            }
        }

        // Lấy danh sách các category để chọn
        List<BlogCategory> categories = categoryService.getAllActiveCategories();
        model.addAttribute("categories", categories);

        return "manageBlog";  // Trả về view
    }








    // 3. Lưu bài viết mới với thumbnail upload
    // Phương thức tạo bài viết mới
    @PostMapping
    public String createPost(@ModelAttribute @Valid PostDTO postDTO,
                             @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                             BindingResult bindingResult,
                             Authentication authentication) throws IOException {

        if (bindingResult.hasErrors()) {
            return "manageBlog";
        }

        // Lấy thông tin tài khoản hiện tại và accountId từ phương thức getAccountUsing
        ResponseEntity<ResponseDTO> accountResponse = getAccountUsing(authentication);
        AccountDTO accountDTO = (AccountDTO) accountResponse.getBody().getData();
        Integer accountId = accountDTO.getAccountId(); // Lấy accountId của người dùng hiện tại

        // Tạo đối tượng Post mới
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setBriefInfo(postDTO.getBriefInfo());
        post.setCreatedDate(LocalDateTime.now());

        // Xử lý thumbnail
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadImage(thumbnail);
            post.setThumbnail(thumbnailUrl);
        } else {
            post.setThumbnail("default-thumbnail.jpg");
        }

        // Gán categoryPost từ PostDTO
        if (postDTO.getCategoryPostID() != null) {
            BlogCategory category = categoryService.getCategoryById(postDTO.getCategoryPostID());
            post.setCategoryPost(category);
        }

        // Gán authorID là accountId của người dùng hiện tại
        Account author = accountService.getAccountByAccountId(accountId);
        post.setAuthor(author);

        // Lưu bài viết vào cơ sở dữ liệu
        postService.createPost(post);
        return "redirect:/admin/blog";
    }


    // 4. Hiển thị form chỉnh sửa bài viết
    @GetMapping("/edit/{postId}")
    public String showUpdatePostForm(@PathVariable Long postId, Model model) {
        Optional<Post> post = postService.getPostById(postId);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            model.addAttribute("categories", categoryService.getAllActiveCategories()); // Hiển thị danh sách danh mục cho form chỉnh sửa
            return "manageBlog";
        } else {
            return "redirect:/admin/blog";
        }
    }

    // 5. Cập nhật bài viết
    @PostMapping("/update/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute @Valid Post postDetails,
                             @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                             BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "manageBlog";
        }

        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadImage(thumbnail);
            postDetails.setThumbnail(thumbnailUrl);
        }

        postService.updatePost(postId, postDetails);
        return "redirect:/admin/blog";
    }

    // 6. Khóa (block) bài viết
    @PostMapping("/block/{postId}")
    public String blockPost(@PathVariable Long postId) {
        postService.blockPost(postId);
        return "redirect:/admin/blog";
    }

    // 7. Mở khóa bài viết
    @PostMapping("/unblock/{postId}")
    public String unblockPost(@PathVariable Long postId) {
        postService.unblockPost(postId);
        return "redirect:/admin/blog";
    }
    @GetMapping("/get-account-using")
    public ResponseEntity<ResponseDTO> getAccountUsing(Authentication authentication) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDTO.builder().message("No Login").build());
            }

            CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
            Account account = accountService.getByUsername(customAccount.getUsername());

            if (account == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseDTO.builder().message("Account not found").build());
            }

            AccountDTO accountDTO = new AccountDTO(
                    account.getAccountId(),
                    account.getAddress(),
                    account.getDateOfBirth(),
                    account.getEmail(),
                    account.getFullName(),
                    account.getPhoneNumber(),
                    account.getUsername(),
                    account.getRole().getRoleName());

            // accountDTO.setCartTotal(cartService.getCartTotal(account)); // Bạn có thể thêm logic cart nếu cần

            responseDTO.setMessage("Success");
            responseDTO.setData(accountDTO);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace(); // Log chi tiết để dễ debug hơn
            return ResponseEntity.internalServerError()
                    .body(ResponseDTO.builder().message("Internal Server Error: " + e.getMessage()).build());
        }
    }
}

