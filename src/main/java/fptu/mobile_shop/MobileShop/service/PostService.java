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
    private BlogCategoryService blogCategoryService; // Inject BlogCategoryService để xử lý danh mục

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public Post createPost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        post.setStatusPost(true); // Bài viết mặc định là hoạt động (not blocked)

        // Kiểm tra và gán categoryPost nếu cần thiết
        if (post.getCategoryPost() != null) {
            BlogCategory category = blogCategoryService.getCategoryById(post.getCategoryPost().getCategoryID());
            post.setCategoryPost(category);
        }

        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post postDetails) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(postDetails.getTitle());
                    post.setContent(postDetails.getContent());
                    post.setBriefInfo(postDetails.getBriefInfo());
                    post.setThumbnail(postDetails.getThumbnail());
                    post.setAuthor(postDetails.getAuthor()); // Cập nhật đối tượng Account (tác giả)

                    // Cập nhật danh mục nếu có
                    if (postDetails.getCategoryPost() != null) {
                        BlogCategory category = blogCategoryService.getCategoryById(postDetails.getCategoryPost().getCategoryID());
                        post.setCategoryPost(category);
                    }

                    post.setStatusPost(postDetails.isStatusPost());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public Page<Post> getPostsByCategoryId(Long categoryId, Pageable pageable) {
        return postRepository.findByCategoryPost_CategoryID(categoryId, pageable); // Truy vấn bài viết theo categoryId
    }
    public List<String> getAllCategories() {
        return blogCategoryService.getAllActiveCategories().stream()
                .map(BlogCategory::getCategoryName)
                .distinct()
                .collect(Collectors.toList());
    }

    public Page<Post> searchByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContaining(title, pageable);
    }

    public void blockPost(Long postId) {
        Post post = getPostById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setStatusPost(false); // Đặt bài viết thành trạng thái không hoạt động (bị block)
        postRepository.save(post);
    }

    public void unblockPost(Long postId) {
        Post post = getPostById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setStatusPost(true); // Đặt bài viết lại thành trạng thái hoạt động
        postRepository.save(post);
    }
}
