package fptu.mobile_shop.MobileShop.service;

import org.json.JSONArray;

public interface ProvinceService {
    JSONArray getProvincesData() throws Exception;
    JSONArray getDistrictsData(int provinceId) throws Exception;
    JSONArray getWardsData(int districtId) throws Exception;
}