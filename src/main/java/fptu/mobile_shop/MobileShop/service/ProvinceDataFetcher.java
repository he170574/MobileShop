package fptu.mobile_shop.MobileShop.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class ProvinceDataFetcher {

    public JSONArray fetchProvincesData() {
        // Thực hiện logic gọi API để lấy dữ liệu
        return new JSONArray();
    }

    public JSONArray fetchDistrictsData(int provinceId) {
        // Thực hiện logic lấy dữ liệu quận/huyện
        return new JSONArray();
    }

    public JSONArray fetchWardsData(int districtId) {
        // Thực hiện logic lấy dữ liệu phường/xã
        return new JSONArray();
    }
}
