package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.entity.Post;
import fptu.mobile_shop.MobileShop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/blog")
public class AdminPostController {

    @Autowired
    private PostService postService;

    // 1. Xem danh sách tất cả các bài viết hoặc tìm kiếm bài viết theo từ khóa
    @GetMapping
    public String getAllPosts(Model model, @PageableDefault(size = 10) Pageable pageable,
                              @RequestParam(required = false) String query) {
        Page<Post> postsPage;
        if (query != null && !query.isEmpty()) {
            postsPage = postService.searchByTitle(query, pageable); // Tạo method searchPosts trong service
        } else {
            postsPage = postService.getAllPosts(pageable);
        }
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("currentPage", postsPage.getNumber());
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("query", query); // Để giữ giá trị tìm kiếm trong ô input
        return "manageBlog";
    }

    // 2. Tạo mới một bài viết (hiển thị form)
    @GetMapping("/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "manageBlog";
    }

    // 3. Lưu bài viết mới
    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        // Truyền authorID vào service
        postService.createPost(post);
        return "redirect:/admin/blog"; // Sau khi tạo xong chuyển hướng về danh sách bài viết
    }

    // 4. Hiển thị form chỉnh sửa bài viết
    @GetMapping("/edit/{postId}")
    public String showUpdatePostForm(@PathVariable Long postId, Model model) {
        Optional<Post> post = postService.getPostById(postId);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "manageBlog";
        } else {
            return "redirect:/admin/blog"; // Chuyển hướng nếu không tìm thấy bài viết
        }
    }

    // 5. Cập nhật bài viết
    @PostMapping("/update/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post postDetails) {
        postService.updatePost(postId, postDetails);
        return "redirect:/admin/blog"; // Sau khi cập nhật xong chuyển hướng về danh sách bài viết
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
