package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.CheckoutDTO;
import fptu.mobile_shop.MobileShop.dto.OrderDTO;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.*;
import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Cart;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.repository.AccountRepository;
import fptu.mobile_shop.MobileShop.service.CartService;
import fptu.mobile_shop.MobileShop.service.OrderService;
import fptu.mobile_shop.MobileShop.service.impl.GHNServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class CheckoutController {

    private final OrderService orderService;
    private final CartService cartService;
    private GHNServiceImpl ghnService;
    private final AccountRepository accountRepository;

    @GetMapping("/checkout")
    public String checkout(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            // Redirect to login if no user is authenticated
            return "redirect:/login";
        }

        System.out.println("Logged-in user email: " + userDetails.getUsername());

        Account account = accountRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found: " + userDetails.getUsername()));

        model.addAttribute("customer", account);
        ResponseEntity<ProvinceResponse> provinceResponse = ghnService.getProvinces();
        if (provinceResponse.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("provinces", provinceResponse.getBody().getData());
        }

        Cart cart = cartService.findByAccountId();
        double totalAmount = cartService.calculateTotalAmount(cart);

        CheckoutDTO checkoutDTO = new CheckoutDTO(new Order(), cart, 0, totalAmount);
        model.addAttribute("checkoutDTO", checkoutDTO);
        model.addAttribute("invoiceDTO", new OrderDTO());

        return "checkout";
    }


    @PostMapping({"/checkout"})
    public String processCheckout(
            @ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Account account = accountRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return "redirect:/";
    }

    @GetMapping("/districts/{provinceId}")
    @ResponseBody
    public ResponseEntity<List<DistrictResponse.ResponseData>> getDistricts(@PathVariable int provinceId) {
        ResponseEntity<DistrictResponse> districtResponse = ghnService.getDistricts(provinceId);
        if (districtResponse != null && districtResponse.getStatusCode() == HttpStatus.OK) {
            List<DistrictResponse.ResponseData> districts = districtResponse.getBody().getData();
            return new ResponseEntity<>(districts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/wards/{districtId}")
    @ResponseBody
    public ResponseEntity<List<WardResponse.ResponseData>> getWards(@PathVariable int districtId) {
        ResponseEntity<WardResponse> wardResponse = ghnService.getWards(districtId);
        if (wardResponse != null && wardResponse.getStatusCode() == HttpStatus.OK) {
            List<WardResponse.ResponseData> wards = wardResponse.getBody().getData();
            return new ResponseEntity<>(wards, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/available-services")
    @ResponseBody
    public ResponseEntity<AvailableServicesResponse> getAvailableServices(@RequestParam int fromDistrict, @RequestParam int toDistrict) {
        ResponseEntity<AvailableServicesResponse> availableServicesResponse = ghnService.getAvailableServices(fromDistrict, toDistrict);
        if (availableServicesResponse != null && availableServicesResponse.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(availableServicesResponse.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/calculate-fee")
    @ResponseBody
    public ResponseEntity<FeeResponse> calculateFee(@RequestParam int fromDistrictId, @RequestParam String fromWardCode,
                                                    @RequestParam int serviceId, @RequestParam int serviceTypeId,
                                                    @RequestParam int toDistrictId, @RequestParam String toWardCode,
                                                    @RequestParam int height, @RequestParam int length, @RequestParam int weight, @RequestParam int width) {
        ResponseEntity<FeeResponse> feeResponse = ghnService.calculateFee(fromDistrictId, fromWardCode, serviceId, serviceTypeId, toDistrictId, toWardCode, height, length, weight, width);
        if (feeResponse != null && feeResponse.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(feeResponse.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

