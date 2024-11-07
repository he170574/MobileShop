package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "CART_ITEM", schema = "MobileShop")
public class CartItem implements Comparable<CartItem>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "CartID", nullable = false)
    private Cart cart;

    @Column(name = "Quantity")
    private int quantity;

    @Override
    public int compareTo(CartItem other) {
        // Compare by product name, quantity, or other criteria as needed
        return this.product.getProductName().compareTo(other.getProduct().getProductName());
    }
}