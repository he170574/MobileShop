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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderHistoryID")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "product_amount")
    private double productAmount;

    @Column(name = "cost")
    private double cost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderID")
    private Order order;
}
