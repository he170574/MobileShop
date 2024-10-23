package fptu.mobile_shop.MobileShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShipperController {
    @GetMapping("/shipper")
    public String getShipperDashboard() {
        return "dashboard";
    }

    @GetMapping("/shipper/trackingOrder")
    public String getTrackingOrder() { return "trackingOrder";
    }

    @GetMapping("/shipper/trackingDetail")
    public String getTrackingDetail() { return "trackingDetail";

    }

}
