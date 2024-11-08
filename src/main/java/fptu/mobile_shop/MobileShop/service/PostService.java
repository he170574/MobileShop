package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.BlogCategory;
import fptu.mobile_shop.MobileShop.entity.Post;
import fptu.mobile_shop.MobileShop.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BlogCategoryService blogCategoryService;
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    // Lấy tất cả bài viết có statusPost = true
    public Page<Post> getAllActivePosts(Pageable pageable) {
        return postRepository.findByStatusPostTrue(pageable); // Lọc bài viết có statusPost = true
    }

    // Lấy bài viết theo ID
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    // Tạo bài viết mới
    public Post createPost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        post.setStatusPost(true); // Bài viết mặc định là hoạt động

        // Kiểm tra và gán categoryPost nếu có
        if (post.getCategoryPost() != null) {
            BlogCategory category = blogCategoryService.getCategoryById(post.getCategoryPost().getCategoryID());
            post.setCategoryPost(category);
        }

        return postRepository.save(post);
    }

    // Tìm kiếm bài viết theo categoryId và tiêu đề, chỉ lấy bài viết có statusPost = true
    public Page<Post> searchByCategoryAndTitle(Long categoryId, String query, Pageable pageable) {
        return postRepository.findByCategoryPost_CategoryIDAndTitleContainingIgnoreCaseAndStatusPostTrue(categoryId, query, pageable);
    }

    // Cập nhật bài viết
    public Post updatePost(Long postId, Post postDetails) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(postDetails.getTitle());
                    post.setContent(postDetails.getContent());
                    post.setBriefInfo(postDetails.getBriefInfo());
                    post.setThumbnail(postDetails.getThumbnail());
                    post.setAuthor(postDetails.getAuthor()); // Cập nhật tác giả

                    // Cập nhật danh mục nếu có
                    if (postDetails.getCategoryPost() != null) {
                        BlogCategory category = blogCategoryService.getCategoryById(postDetails.getCategoryPost().getCategoryID());
                        post.setCategoryPost(category);
                    }

                    post.setStatusPost(postDetails.isStatusPost()); // Cập nhật trạng thái bài viết
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
    }

    // Xóa bài viết
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // Lấy bài viết theo categoryId và chỉ lấy bài viết có statusPost = true
    public Page<Post> getPostsByCategoryId(Long categoryId, Pageable pageable) {
        return postRepository.findByCategoryPost_CategoryIDAndStatusPostTrue(categoryId, pageable);
    }

    // Lấy tất cả các danh mục bài viết
    public List<String> getAllCategories() {
        return blogCategoryService.getAllActiveCategories().stream()
                .map(BlogCategory::getCategoryName)
                .distinct()
                .collect(Collectors.toList());
    }

    // Tìm kiếm bài viết theo tiêu đề và chỉ lấy bài viết có statusPost = true
    public Page<Post> searchByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContainingAndStatusPostTrue(title, pageable);
    }

    // Block bài viết (thay đổi statusPost thành false)
    public void blockPost(Long postId) {
        Post post = getPostById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setStatusPost(false); // Đặt bài viết thành trạng thái không hoạt động
        postRepository.save(post);
    }

    // Unblock bài viết (thay đổi statusPost thành true)
    public void unblockPost(Long postId) {
        Post post = getPostById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setStatusPost(true); // Đặt bài viết lại thành trạng thái hoạt động
        postRepository.save(post);
    }
}

