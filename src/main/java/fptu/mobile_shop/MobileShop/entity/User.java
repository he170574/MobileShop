package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users", schema = "MobileShop")
@Data  // Tự động tạo getter, setter, toString, equals, và hashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")  // Đảm bảo đúng tên cột trong database
    private Long userID;

    @Column(name = "UserName", nullable = false)
    private String userName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;

    @Column(name = "RegistrationDate")
    private LocalDate registrationDate;

    @Column(name = "Address")
    private String address;

    @Column(name = "PaymentInfo")
    private String paymentInfo;

    // Many-to-One relationship with UserRole
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID")  // Liên kết với bảng UserRole
    private UserRole role;
}
