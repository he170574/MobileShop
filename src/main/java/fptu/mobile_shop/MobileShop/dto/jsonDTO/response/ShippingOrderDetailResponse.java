package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingOrderDetailResponse implements Serializable {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private ResponseData data;

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseData {
        @JsonProperty("order_info")
        private OrderInfo orderInfo;

        @JsonProperty("tracking_logs")
        private List<TrackingLog> trackingLogs;

        public void getSortTrackingLogsByActionAt() {
            if (trackingLogs != null) {
                // Sort by getFormattedActionAt in descending order
                Collections.sort(trackingLogs, Comparator.comparing(TrackingLog::getFormattedActionAt).reversed());
            }
        }

        @JsonProperty("ticket_logs")
        private List<?> ticketLogs;

        @JsonProperty("is_sender")
        private boolean isSender;

        @Data
        @EqualsAndHashCode
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class OrderInfo {
            @JsonProperty("order_code")
            private String orderCode;
            @JsonProperty("shop_id")
            private Long shopId;
            @JsonProperty("status")
            private String status;
            @JsonProperty("status_name")
            private String statusName;
            @JsonProperty("picktime")
            private String pickTime;
            @JsonProperty("leadtime")
            private String leadTime;
            @JsonProperty("finish_date")
            private String finishDate;
            @JsonProperty("to_name")
            private String toName;
            @JsonProperty("to_phone")
            private String toPhone;
            @JsonProperty("to_address")
            private String toAddress;
            @JsonProperty("from_name")
            private String fromName;
            @JsonProperty("from_phone")
            private String fromPhone;
            @JsonProperty("from_address")
            private String fromAddress;
            @JsonProperty("return_name")
            private String returnName;
            @JsonProperty("return_phone")
            private String returnPhone;
            @JsonProperty("return_address")
            private String returnAddress;
            @JsonProperty("payment_type_id")
            private int paymentTypeId;
            @JsonProperty("order_version")
            private String orderVersion;
            @JsonProperty("is_partial_return")
            private boolean isPartialReturn;
            @JsonProperty("danger_zone_sender")
            private boolean dangerZoneSender;
            @JsonProperty("danger_zone_deliver")
            private boolean dangerZoneDeliver;
            @JsonProperty("sub")
            private int sub;
            @JsonProperty("is_sss")
            private boolean isSss;

            public String getFormattedPickTime() {
                if (pickTime != null && !pickTime.isEmpty()) {
                    LocalDateTime dateTime = LocalDateTime.parse(pickTime, DateTimeFormatter.ISO_DATE_TIME);
                    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
                return null;
            }

            public String getFormattedLeadTime() {
                if (leadTime != null && !leadTime.isEmpty()) {
                    LocalDateTime dateTime = LocalDateTime.parse(leadTime, DateTimeFormatter.ISO_DATE_TIME);
                    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
                return null;
            }
        }

        @Data
        @EqualsAndHashCode
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TrackingLog {
            @JsonProperty("order_code")
            private String orderCode;
            @JsonProperty("status")
            private String status;
            @JsonProperty("status_name")
            private String statusName;
            @JsonProperty("location")
            private Location location;
            @JsonProperty("executor")
            private Executor executor;
            @JsonProperty("action_at")
            private String actionAt;
            @JsonProperty("sync_data_at")
            private String syncDataAt;

            // Convert actionAt from GMT+0 to GMT+7 and format the date
            public String getFormattedActionAt() {
                if (actionAt != null && !actionAt.isEmpty()) {
                    ZonedDateTime dateTime = ZonedDateTime.parse(actionAt, DateTimeFormatter.ISO_DATE_TIME)
                            .withZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
                    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
                return null;
            }

            // Convert syncDataAt from GMT+0 to GMT+7 and format the date
            public String getFormattedSyncDataAt() {
                if (syncDataAt != null && !syncDataAt.isEmpty()) {
                    ZonedDateTime dateTime = ZonedDateTime.parse(syncDataAt, DateTimeFormatter.ISO_DATE_TIME)
                            .withZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
                    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
                return null;
            }
        }

        @Data
        @EqualsAndHashCode
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Location {
            @JsonProperty("address")
            private String address;
            @JsonProperty("ward_code")
            private String wardCode;
            @JsonProperty("district_id")
            private int districtId;
            @JsonProperty("warehouse_id")
            private long warehouseId;
        }

        @Data
        @EqualsAndHashCode
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Executor {
            @JsonProperty("employee_id")
            private long employeeId;
            @JsonProperty("name")
            private String name;
            @JsonProperty("phone")
            private String phone;
            @JsonProperty("operation_partner")
            private String operationPartner;
        }
    }
}