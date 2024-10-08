package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "userroles", schema = "MobileShop")
public class UserRole {

    @Id
    @Column(name = "RoleID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleID;

    @Column(name = "RoleName", nullable = false)
    private String roleName;
}
