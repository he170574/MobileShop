package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.PostDTO;
import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.BlogCategory;
import fptu.mobile_shop.MobileShop.entity.Post;
import fptu.mobile_shop.MobileShop.service.AccountService;
import fptu.mobile_shop.MobileShop.service.BlogCategoryService;
import fptu.mobile_shop.MobileShop.service.PostService;
import fptu.mobile_shop.MobileShop.service.CloudinaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    @GetMapping("/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new PostDTO());
        model.addAttribute("categories", categoryService.getAllActiveCategories()); // Hiển thị danh sách danh mục cho form tạo bài viết
        return "manageBlog";
    }

    // 3. Lưu bài viết mới với thumbnail upload
    @PostMapping
    public String createPost(@ModelAttribute @Valid PostDTO postDTO,
                             @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                             BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "manageBlog";
        }

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

        // Lấy và gán categoryPost và author từ PostDTO
        if (postDTO.getCategoryPostID() != null) {
            BlogCategory category = categoryService.getCategoryById(postDTO.getCategoryPostID());
            post.setCategoryPost(category);
        }

        if (postDTO.getAuthorID() != null) {
            Account author = accountService.getAccountByAccountId(postDTO.getAuthorID().intValue());
            post.setAuthor(author);
        }

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
}

