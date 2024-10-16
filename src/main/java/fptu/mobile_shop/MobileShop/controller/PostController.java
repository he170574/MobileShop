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

@Controller
@RequestMapping("/blog")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public String getAllPosts(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Post> postsPage = postService.getAllPosts(pageable);
        model.addAttribute("posts", postsPage.getContent()); // Danh sách bài viết
        model.addAttribute("currentPage", postsPage.getNumber()); // Số trang hiện tại
        model.addAttribute("totalPages", postsPage.getTotalPages()); // Tổng số trang
        return "blog"; // Tên template cho danh sách bài viết
    }

    @GetMapping("/{postId}")
    public String getPostById(@PathVariable Long postId, Model model) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
        model.addAttribute("post", post);
        return "blog-single"; // Tên template cho bài viết đơn
    }

    @GetMapping("/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "create-post"; // Tên template cho form tạo bài viết
    }

    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        postService.createPost(post);
        return "redirect:/blog"; // Chuyển hướng đến danh sách bài viết
    }

    @GetMapping("/edit/{postId}")
    public String showUpdatePostForm(@PathVariable Long postId, Model model) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
        model.addAttribute("post", post);
        return "edit-post"; // Tên template cho form chỉnh sửa bài viết
    }

    @PostMapping("/update/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post postDetails) {
        postService.updatePost(postId, postDetails);
        return "redirect:/blog"; // Chuyển hướng đến danh sách bài viết
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/blog"; // Chuyển hướng đến danh sách bài viết
    }
}


