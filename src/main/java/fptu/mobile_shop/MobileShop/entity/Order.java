package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderID")
    private Long id;

    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Column(name = "ACCOUNT_ID")
    private Integer accountId;

    @Column(name = "OrderStatus", length = 50)
    private String orderStatus;

    @Column(name = "OrderCode", length = 50)
    private String orderCode;

    @Column(name = "TotalAmount")
    private double totalAmount;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;
}
