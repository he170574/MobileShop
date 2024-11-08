package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ShippingOrderResponse implements Serializable {
    @JsonProperty("data")
    private ResponseData data;

    private String error;

    @Data
    @EqualsAndHashCode
    @ToString
    @NoArgsConstructor
    public static class ResponseData {
        @JsonProperty("order_code")
        private String order_code;

        @JsonProperty("expected_delivery_time")
        private String expected_delivery_time;

        @JsonProperty("total_fee")
        private String total_fee;

        // Method to get the formatted date
        public String getFormattedExpectedDeliveryTime() {
            if (expected_delivery_time != null && !expected_delivery_time.isEmpty()) {
                // Parse and format the date
                LocalDateTime dateTime = LocalDateTime.parse(expected_delivery_time, DateTimeFormatter.ISO_DATE_TIME);
                return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            return null; // Return null if the expected_delivery_time is null or empty
        }
    }
}

