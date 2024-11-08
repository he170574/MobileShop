package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BlogCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private Boolean status = true; // TRUE: kích hoạt, FALSE: bị chặn
}
