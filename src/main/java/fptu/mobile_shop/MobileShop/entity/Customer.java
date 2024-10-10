package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users", schema = "MobileShop")
public class Customer{
    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name = "UserName", nullable = false)
    private String userName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;

    @Column(name = "RegistrationDate", nullable = false)
    private java.time.LocalDateTime registrationDate;

    @Column(name = "Address")
    private String address;

    @Column(name = "PaymentInfo")
    private String paymentInfo;

    @Column(name = "PhoneNumber")
    private String phoneNumber;
}
