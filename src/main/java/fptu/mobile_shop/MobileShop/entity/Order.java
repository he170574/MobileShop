package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ORDERS", schema = "MobileShop")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Long id;

    @Column(name = "OrderCode")
    private String orderCode;

    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "OrderStatus", length = 50)
    private String orderStatus;

    @Column(name = "ExpectedDeliveryTime")
    private String expectedDeliveryTime;

    @Column(name = "ShippingFee")
    private BigDecimal shippingFee;

    @Column(name = "ShippingCode", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String shippingCode;

    @Column(name = "TotalAmount")
    private double totalAmount;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return (orderDetails != null || orderDetails.size() <= 0) ? orderDetails : new ArrayList<>();
    }
}
