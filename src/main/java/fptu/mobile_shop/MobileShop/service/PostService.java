package fptu.mobile_shop.MobileShop.service;

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

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public Post createPost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post postDetails) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(postDetails.getTitle());
                    post.setContent(postDetails.getContent());
                    post.setBriefInfo(postDetails.getBriefInfo());
                    post.setThumbnail(postDetails.getThumbnail());
                    post.setCounts(postDetails.getCounts());
                    post.setAuthor(postDetails.getAuthor()); // Cập nhật đối tượng Account (tác giả)
                    post.setCategoryPost(postDetails.getCategoryPost());
                    post.setStatusPost(postDetails.getStatusPost());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public Page<Post> getPostsByCategory(String category, Pageable pageable) {
        return postRepository.findByCategoryPost(category, pageable);
    }

    public List<String> getAllCategories() {
        return postRepository.findAll().stream()
                .map(Post::getCategoryPost)
                .distinct() // Lấy các danh mục duy nhất
                .collect(Collectors.toList());
    }

    public Page<Post> searchByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContaining(title, pageable);
    }
}


