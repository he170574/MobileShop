package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@Data // Lombok annotation for getters, setters, equals, hash, toString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String briefInfo;

    private String thumbnail;

    private int counts = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorID", referencedColumnName = "ACCOUNT_ID", nullable = false)
    private Account author; // Liên kết với tài khoản

    private LocalDateTime createdDate;

    private String categoryPost;

    private String statusPost;
}