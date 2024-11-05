package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productcomment")
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentID")
    private Integer commentId;

    @JoinColumn(name = "ProductID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "ACCOUNT_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;

    @JoinColumn(name = "Order_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    @Column(name = "CommentText")
    private String commentText;

    @Column(name = "CommentDate")
    private LocalDateTime commentDate;

    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("0")
    private Boolean isDeleted = false;
}
