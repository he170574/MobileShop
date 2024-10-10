package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "productcategory", schema = "MobileShop")
public class Category {
    @Id
    @Column(name = "CategoryID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CategoryID;

    @Column(name = "CategoryName")
    private String CategoryName;

    @Column(name = "DELETED", nullable = false)
    private Boolean DELETED;


}
