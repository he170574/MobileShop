package fptu.mobile_shop.MobileShop.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    private String transactionCode;
    private String description;
    private double value;
    private String date;
    private String accountNumber;
    private String referenceCode;
    private double balance;
    private String virtualAccountNumber;
    private String virtualAccountName;
    private String counterpartAccountNumber;
    private String counterpartAccountName;
    private String bankBIN;
    private String counterpartBankName;
}
