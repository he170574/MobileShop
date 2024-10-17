package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.service.ProvinceService;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    // Inject ProvinceService vào controller
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    // API để lấy danh sách các tỉnh/thành phố
    @GetMapping
    public ResponseEntity<String> getProvinces() {
        try {
            JSONArray provinces = provinceService.getProvincesData();
            return ResponseEntity.ok(provinces.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching provinces: " + e.getMessage());
        }
    }

    // API để lấy danh sách quận/huyện theo tỉnh/thành phố
    @GetMapping("/{provinceId}/districts")
    public ResponseEntity<String> getDistricts(@PathVariable int provinceId) {
        try {
            JSONArray districts = provinceService.getDistrictsData(provinceId);
            return ResponseEntity.ok(districts.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching districts: " + e.getMessage());
        }
    }

    // API để lấy danh sách phường/xã theo quận/huyện
    @GetMapping("/districts/{districtId}/wards")
    public ResponseEntity<String> getWards(@PathVariable int districtId) {
        try {
            JSONArray wards = provinceService.getWardsData(districtId);
            return ResponseEntity.ok(wards.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching wards: " + e.getMessage());
        }
    }
}
