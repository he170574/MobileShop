package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.entity.Post;
import fptu.mobile_shop.MobileShop.entity.BlogCategory;
import fptu.mobile_shop.MobileShop.service.PostService;
import fptu.mobile_shop.MobileShop.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private BlogCategoryService blogCategoryService;

    // Phương thức lấy tất cả bài viết
    @GetMapping
    public String getAllPosts(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Post> postsPage = postService.getAllPosts(pageable);
        List<BlogCategory> categories = blogCategoryService.getAllActiveCategories(); // Lấy danh mục hoạt động
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", postsPage.getNumber());
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("categories", categories); // Gán danh mục vào model
        model.addAttribute("selectedCategory", "All"); // Gán danh mục đã chọn là "All"
        return "blog";
    }

    // Phương thức lấy bài viết theo category
    @GetMapping("/category/{categoryId}")
    public String getPostsByCategory(@PathVariable Long categoryId, Model model, @PageableDefault(size = 5) Pageable pageable) {
        // Lấy bài viết theo categoryId
        Page<Post> postsPage = postService.getPostsByCategoryId(categoryId, pageable);

        // Lấy tất cả các danh mục để hiển thị ở sidebar
        List<BlogCategory> categories = blogCategoryService.getAllActiveCategories();

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", postsPage.getNumber());
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categoryId); // Truyền categoryId vào model
        return "blog"; // Trả về trang blog.html
    }



    // Phương thức lấy bài viết theo ID
    @GetMapping("/{postId}")
    public String getPostById(@PathVariable Long postId, Model model) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
        List<BlogCategory> categories = blogCategoryService.getAllActiveCategories();
        model.addAttribute("post", post);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", "All");
        return "blog-single";
    }

    // Phương thức tạo bài viết mới
    @GetMapping("/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        List<BlogCategory> categories = blogCategoryService.getAllActiveCategories(); // Danh mục
        model.addAttribute("categories", categories);
        return "create-post";
    }

    // Phương thức sửa bài viết
    @GetMapping("/edit/{postId}")
    public String showUpdatePostForm(@PathVariable Long postId, Model model) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
        List<BlogCategory> categories = blogCategoryService.getAllActiveCategories(); // Danh mục
        model.addAttribute("post", post);
        model.addAttribute("categories", categories);
        return "edit-post";
    }

    // Phương thức cập nhật bài viết
    @PostMapping("/update/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post postDetails) {
        postService.updatePost(postId, postDetails);
        return "redirect:/blog";
    }

    // Phương thức xóa bài viết
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/blog";
    }

    // Phương thức tìm kiếm bài viết
    @GetMapping("/search")
    public String searchPosts(@RequestParam("title") String title, Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Post> postsPage = postService.searchByTitle(title, pageable);
        List<BlogCategory> categories = blogCategoryService.getAllActiveCategories();
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", postsPage.getNumber());
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", "All");
        return "blog";
    }
}
