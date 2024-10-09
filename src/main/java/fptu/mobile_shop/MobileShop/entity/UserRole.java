package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "userroles", schema = "MobileShop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")  // Đảm bảo đúng tên cột trong database
    private Integer roleID;

    @Column(name = "RoleName", nullable = false)
    private String roleName;

    // One-to-Many relationship with User
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users;
}
