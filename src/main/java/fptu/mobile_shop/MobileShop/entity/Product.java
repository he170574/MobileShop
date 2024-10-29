package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "PRODUCT", schema = "MobileShop")
public class Product {
    @Id
    @Column(name = "ProductID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productID;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "ProductDetails")
    private String productDetails;

    @Column(name = "ProductImage")
    private String productImage;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Cost")
    private Double cost;

    @Column(name = "CategoryName")
    private String categoryName;

    @Column(name = "StockQuantity")
    private Integer stockQuantity;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

}
