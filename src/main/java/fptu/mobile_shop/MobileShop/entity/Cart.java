package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "CART", schema = "MobileShop")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Long id;

    @Column(name = "AccountID")
    private int accountID;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> items;

    public List<CartItem> getItems() {
        return (CollectionUtils.isEmpty(items)) ? new ArrayList<>() : items;
    }
}
