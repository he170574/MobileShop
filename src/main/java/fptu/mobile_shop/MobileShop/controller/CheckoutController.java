package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.CheckoutDTO;
import fptu.mobile_shop.MobileShop.dto.OrderDTO;
import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.*;
import fptu.mobile_shop.MobileShop.entity.*;
import fptu.mobile_shop.MobileShop.final_attribute.STATUS;
import fptu.mobile_shop.MobileShop.repository.AccountRepository;
import fptu.mobile_shop.MobileShop.repository.CartItemRepository;
import fptu.mobile_shop.MobileShop.service.CartService;
import fptu.mobile_shop.MobileShop.service.OrderDetailService;
import fptu.mobile_shop.MobileShop.service.OrderService;
import fptu.mobile_shop.MobileShop.service.impl.GHNServiceImpl;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
public class CheckoutController {

    @Resource
    private final OrderService orderService;

    @Resource
    private final OrderDetailService orderDetailService;

    @Resource
    private final CartService cartService;

    private GHNServiceImpl ghnService;
    private final AccountRepository accountRepository;
    private final CartItemRepository cartItemRepository;

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
        if (Objects.isNull(cart) || CollectionUtils.isEmpty(cart.getItems())) {
            return "redirect:/home";
        }

        Long cartId = cart.getId();  // Retrieve the cart ID

// Calculate total amount and quantity
        double totalAmount = cartService.calculateTotalAmount(cart);
        AtomicInteger soLuong = new AtomicInteger();
        cart.getItems().forEach(cartItem -> {
            soLuong.addAndGet(cartItem.getQuantity());
        });

        CheckoutDTO checkoutDTO = new CheckoutDTO(new Order(), cart, 0, totalAmount);
        checkoutDTO.setTotalHeight(soLuong.get() * 3);  // Total height
        checkoutDTO.setTotalWeight(soLuong.get() * 5);  // Total weight
        checkoutDTO.setTotalWidth(30);                  // Total width
        checkoutDTO.setTotalLength(30);                 // Total length

// Retrieve cart items using the cart ID
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

        if (!cartItems.isEmpty()) {
            List<String> productNames = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();

            for (CartItem cartItem : cartItems) {
                productNames.add(cartItem.getProduct().getProductName());
                quantities.add(cartItem.getQuantity());
            }

            model.addAttribute("productNames", productNames);
            model.addAttribute("quantities", quantities);
        }
        model.addAttribute("orderDTO", cartId);
        model.addAttribute("checkoutDTO", checkoutDTO);
        model.addAttribute("invoiceDTO", new OrderDTO());

        return "checkout";
    }


    @PostMapping({"/checkout"})
    public String processCheckout(Model model,
                                  @ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal UserDetails userDetails
    ) {

        Account account = accountRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Cart cart = cartService.findByAccountId();
        if (Objects.isNull(cart) || CollectionUtils.isEmpty(cart.getItems())) {
            return "redirect:/home";
        }
        AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);
        Order order = new Order();
        order.setOrderDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        order.setAccount(account);
        order.setTotalAmount(totalAmount.get() + checkoutDTO.getShippingFee());
        order.setShippingFee(new BigDecimal(checkoutDTO.getShippingFee()));
        order.setOrderStatus(STATUS.INIT);
//        order = orderService.createOrder(order);
        final Order createdOrder = orderService.createOrder(order);
        cart.getItems().forEach(orderItem -> {
            totalAmount.updateAndGet(v -> v + (orderItem.getQuantity() * orderItem.getProduct().getPrice()));
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(orderItem.getQuantity());
            orderDetail.setProduct(orderItem.getProduct());
            if(orderItem.getProduct().getCost() == null){
                orderDetail.setCost(0);
            }else {
                orderDetail.setCost(orderItem.getProduct().getCost());
            }
            orderDetail.setProductAmount(orderItem.getProduct().getPrice() * orderItem.getQuantity());
            orderDetail.setProductName(orderItem.getProduct().getProductName());
            orderDetail.setOrder(createdOrder);
            orderDetailService.createOrderDetail(orderDetail);
        });


        createdOrder.setTotalAmount(totalAmount.get() + checkoutDTO.getShippingFee());
        createdOrder.setOrderStatus(STATUS.WATING_PAYMENT);
        orderService.createOrder(createdOrder);

        cartService.deleteCart(cart);
        model.addAttribute("bankId", 970422);
        model.addAttribute("soTaiKhoan", "3854019201");
        model.addAttribute("totalAmount", order.getTotalAmount());
        model.addAttribute("noiDung", "ThanhToanDonHang_" + account.getUsername() + "_id_");
        model.addAttribute("accountName", "Do Hoai Linh");
//        https://vietqr.io/danh-sach-api/link-tao-ma-nhanh/
        return "payment";
    }

    @PostMapping({"/confirmPayment"})
    public ResponseEntity<ResponseDTO>  confirmPayment(){
        ResponseDTO responseDTO = new ResponseDTO();

        return ResponseEntity.internalServerError().body(responseDTO);
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

