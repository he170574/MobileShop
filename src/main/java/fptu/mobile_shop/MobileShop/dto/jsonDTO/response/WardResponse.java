package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WardResponse {
    @JsonProperty("data")
    private List<ResponseData> data;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseData {
        @JsonProperty("WardCode")
        private String WardCode;
        @JsonProperty("DistrictID")
        private int districtId;
        @JsonProperty("WardName")
        private String WardName;
    }
}
