package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ORDERHISTORY", schema = "MobileShop")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderHistoryID")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productAmount")
    private double productAmount;

    @Column(name = "cost")
    private double cost;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID")
    private Order order;
}
