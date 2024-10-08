package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users", schema = "MobileShop")
public class UserWithRole {

    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name = "UserName", nullable = false)
    private String userName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "RegistrationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(name = "Address")
    private String address;

    @Column(name = "MobilePhone")
    private String mobilePhone;

    // Relationship with UserRole
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID")
    private UserRole role;
}
