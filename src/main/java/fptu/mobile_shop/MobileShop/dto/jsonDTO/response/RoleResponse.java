package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import fptu.mobile_shop.MobileShop.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Integer roleId;
    private String roleName;

    public RoleResponse(Role role) {
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
    }
}
