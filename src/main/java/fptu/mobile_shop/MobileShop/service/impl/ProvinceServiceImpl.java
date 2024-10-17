package fptu.mobile_shop.MobileShop.service.impl;


import fptu.mobile_shop.MobileShop.service.ProvinceDataFetcher;
import fptu.mobile_shop.MobileShop.service.ProvinceService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceDataFetcher provinceDataFetcher;

    // Inject ProvinceDataFetcher vào ServiceImpl
    @Autowired
    public ProvinceServiceImpl(ProvinceDataFetcher provinceDataFetcher) {
        this.provinceDataFetcher = provinceDataFetcher;
    }

    @Override
    public JSONArray getProvincesData() throws Exception {
        return provinceDataFetcher.fetchProvincesData();
    }

    @Override
    public JSONArray getDistrictsData(int provinceId) throws Exception {
        return provinceDataFetcher.fetchDistrictsData(provinceId);
    }

    @Override
    public JSONArray getWardsData(int districtId) throws Exception {
        return provinceDataFetcher.fetchWardsData(districtId);
    }
}
