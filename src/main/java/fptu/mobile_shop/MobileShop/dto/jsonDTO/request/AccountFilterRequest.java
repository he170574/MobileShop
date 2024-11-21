package fptu.mobile_shop.MobileShop.dto.jsonDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFilterRequest {
    private String keyword;
    private List<String> roles;
    private Integer pageNum = 1;
    private Integer pageSize = 20;

    public List<String> getRoles() {
        return CollectionUtils.isEmpty(roles) ? new ArrayList<>() : roles;
    }
}
