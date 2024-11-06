package fptu.mobile_shop.MobileShop.dto.jsonDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShippingOrderRequest {
    private String to_name;
    private String from_name;
    private String from_phone;
    private String from_address;
    private String from_ward_name;
    private String from_district_name;
    private String from_province_name;
    private String to_phone;
    private String to_address;
    private String to_ward_code;
    private int to_district_id;
    private int cod_amount;
    private String to_ward_name;
    private String to_district_name;
    private int weight;
    private int length;
    private int width;
    private int height;
    private int service_type_id;
    private int service_id;
    private int payment_type_id;
    private String required_note;
    private List<Item> items;

    @Data
    @ToString
    public static class Item {
        private String name;
        private int quantity;
        private int weight;
    }
}

