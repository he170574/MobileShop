package fptu.mobile_shop.MobileShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceDto {
    private String email;
    private String province;
    private String districtName;
    private String districtId;
    private String wardName;
    private String wardCode;
    private String address;
    private String serviceId;
    private String shortName;
    private String serviceTypeId;
    private String note;
}
