package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users", schema = "MobileShop")
@Data  // Lombok tự động thêm getter, setter, toString, equals và hashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String userName;
    private String email;
    private String passwordHash;
    private LocalDate registrationDate;
    private String address;
    private String paymentInfo;
}
