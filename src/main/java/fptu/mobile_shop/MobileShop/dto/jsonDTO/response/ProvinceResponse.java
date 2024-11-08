package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvinceResponse implements Serializable {
    @JsonProperty("data")
    private List<ResponseData> data;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseData {
        @JsonProperty("ProvinceID")
        private int provinceId;

        @JsonProperty("ProvinceName")
        private String provinceName;
    }
}
