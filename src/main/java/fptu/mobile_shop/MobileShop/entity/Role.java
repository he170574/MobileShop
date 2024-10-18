package fptu.mobile_shop.MobileShop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ROLE", schema = "MobileShop")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "ROLE_NAME", nullable = false)
    @Nationalized
    private String roleName;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Account> accounts;
}

