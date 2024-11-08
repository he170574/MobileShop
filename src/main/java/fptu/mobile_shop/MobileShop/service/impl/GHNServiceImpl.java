package fptu.mobile_shop.MobileShop.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.ShippingOrderRequest;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GHNServiceImpl {

//    private final String GHN_API_URL_CREATE = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";
//    private final String GHN_API_URL_PROVINCE = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
//    private final String GHN_API_URL_DISTRICT = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
//    private final String GHN_API_URL_WARD = "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id";
//    private final String GHN_API_URL_AVAILABLE_SERVICES = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";
//    private final String GHN_API_URL_FEE = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
//    private final String GHN_API_URL_CANCEL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel";
//    private final String GHN_SHOP_ID = "5209379";
//    private final int GHN_SHOP_ID_INT = 5209379;
//    private final String GHN_TOKEN = "062f0f06-4ad9-11ef-b2f2-76c43f98f323";

    private final String GHN_API_URL_CREATE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";
    private final String GHN_API_URL_PROVINCE = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
    private final String GHN_API_URL_DISTRICT = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
    private final String GHN_API_URL_WARD = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id";
    private final String GHN_API_URL_AVAILABLE_SERVICES = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";
    private final String GHN_API_URL_FEE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private final String GHN_API_URL_CANCEL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel";
    private final String GHN_SHOP_ID = "192829";
    private final int GHN_SHOP_ID_INT = 192829;
    private final String GHN_TOKEN = "3b87f02d-415f-11ef-8e53-0a00184fe694";

    private final String GHN_API_URL_TRACKING_LOGS = "https://fe-online-gateway.ghn.vn/order-tracking/public-api/client/tracking-logs";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity<ProvinceResponse> getProvinces() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", GHN_TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ProvinceResponse> response = restTemplate.exchange(GHN_API_URL_PROVINCE, HttpMethod.GET, entity, ProvinceResponse.class);
        return response;
    }

    public ResponseEntity<DistrictResponse> getDistricts(int provinceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", GHN_TOKEN);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GHN_API_URL_DISTRICT).queryParam("province_id", provinceId);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DistrictResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, DistrictResponse.class);
        return response;
    }

    public ResponseEntity<WardResponse> getWards(int districtId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", GHN_TOKEN);

        Map<String, Integer> requestMap = Collections.singletonMap("district_id", districtId);
        HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(requestMap, headers);

        ResponseEntity<WardResponse> response = restTemplate.exchange(GHN_API_URL_WARD, HttpMethod.POST, entity, WardResponse.class);
        return response;
    }

    public ResponseEntity<FeeResponse> calculateFee(int fromDistrictId, String fromWardCode, int serviceId, int serviceTypeId, int toDistrictId, String toWardCode, int height, int length, int weight, int width) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("ShopId", GHN_SHOP_ID);
        headers.set("Token", GHN_TOKEN);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from_district_id", fromDistrictId);
        requestBody.put("from_ward_code", fromWardCode);
        requestBody.put("service_id", serviceId);
        requestBody.put("service_type_id", serviceTypeId);
        requestBody.put("to_district_id", toDistrictId);
        requestBody.put("to_ward_code", toWardCode);
        requestBody.put("height", height);
        requestBody.put("length", length);
        requestBody.put("weight", weight);
        requestBody.put("width", width);
        requestBody.put("coupon", "");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<FeeResponse> response = restTemplate.exchange(GHN_API_URL_FEE, HttpMethod.POST, entity, FeeResponse.class);
        return response;
    }

    public ResponseEntity<AvailableServicesResponse> getAvailableServices(int fromDistrict, int toDistrict) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", GHN_TOKEN);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("shop_id", GHN_SHOP_ID_INT);
        requestBody.put("from_district", fromDistrict);
        requestBody.put("to_district", toDistrict);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<AvailableServicesResponse> response = restTemplate.exchange(GHN_API_URL_AVAILABLE_SERVICES, HttpMethod.POST, entity, AvailableServicesResponse.class);
        return response;
    }

    public ResponseEntity<ShippingOrderResponse> createShippingOrder(ShippingOrderRequest request) {
        ShippingOrderResponse response = new ShippingOrderResponse();
        try {
            RestTemplate res = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("ShopId", GHN_SHOP_ID);
            headers.set("Token", GHN_TOKEN);

            String body = objectMapper.writeValueAsString(request);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            System.out.println("Sending request to GHN API...");
            System.out.println("Headers: " + headers);
            System.out.println("Body: " + body);

            ResponseEntity<ShippingOrderResponse> apiResponse = res.postForEntity(GHN_API_URL_CREATE, entity, ShippingOrderResponse.class);

            System.out.println("Response from GHN API: " + apiResponse);
            response = apiResponse.getBody();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error sending request to GHN API: " + e.getMessage());
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<ShippingOrderDetailResponse> getShippingOrderDetail(String orderCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", GHN_TOKEN);

        Map<String, String> requestBody = Collections.singletonMap("order_code", orderCode);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ShippingOrderDetailResponse> response = restTemplate.exchange(GHN_API_URL_TRACKING_LOGS, HttpMethod.POST, entity, ShippingOrderDetailResponse.class);

        if (response.getBody() != null) {
            // Log the response for debugging
            System.out.println("Response: " + response.getBody());
        } else {
            System.out.println("Response body is null");
        }

        return response;
    }
}
