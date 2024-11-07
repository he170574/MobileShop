package fptu.mobile_shop.MobileShop.dto.jsonDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@lombok.Data
@Getter
@Setter
public class RecordPayment {
    private long id;
    private String tid;
    private String description;
    private int amount;
    private Double cusumBalance;
    private LocalDateTime when;
    private LocalDateTime bookingDate;
    private String bankSubAccId;
    private String paymentChannel;
    private String virtualAccount;
    private String virtualAccountName;
    private String corresponsiveName;
    private String corresponsiveAccount;
    private String corresponsiveBankId;
    private String corresponsiveBankName;
    private int accountId;
    private String bankCodeName;
}
