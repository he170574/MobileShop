package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.CheckoutDTO;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.ShippingOrderRequest;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.response.ShippingOrderResponse;
import fptu.mobile_shop.MobileShop.entity.Cart;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.entity.OrderDetail;
import fptu.mobile_shop.MobileShop.final_attribute.STATUS;
import fptu.mobile_shop.MobileShop.repository.AccountRepository;
import fptu.mobile_shop.MobileShop.repository.OrderRepository;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import fptu.mobile_shop.MobileShop.service.AccountService;
import fptu.mobile_shop.MobileShop.service.CartService;
import fptu.mobile_shop.MobileShop.service.OrderDetailService;
import fptu.mobile_shop.MobileShop.service.OrderService;
import fptu.mobile_shop.MobileShop.service.impl.GHNServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/ghn")
public class GHNController {

    @Autowired
    private GHNServiceImpl ghnService;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderRepository cartRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/list-order")
    public String listOrder(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(required = false) String status) {
        Page<Order> orderPage = orderService.getListOrder(keyword, status, page, size);
        model.addAttribute("orders", orderPage);
        model.addAttribute("currentPage", orderPage.getNumber());
        model.addAttribute("size", size);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status); // Add status to model
        return "orderHistory";
    }

    @PostMapping("/create-order")
    public String createShippingOrder(@ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
                                      @ModelAttribute ShippingOrderRequest request,
                                      @RequestParam("ghn_shipping_fee") BigDecimal ghnShippingFee,
                                      RedirectAttributes redirectAttrs, Model model,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        Cart cart = cartService.findByAccountId();
        Order order = new Order();
//        if()
        ResponseEntity<ShippingOrderResponse> response = ghnService.createShippingOrder(request);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            ShippingOrderResponse responseData = response.getBody();
            String shippingCode = responseData.getData().getOrder_code();
            String expectedDeliveryTime = responseData.getData().getFormattedExpectedDeliveryTime();
            String totalAmount = responseData.getData().getTotal_fee();

            Random random = new Random();

            // Generate 3 random uppercase letters
            String letters = "";
            for (int i = 0; i < 3; i++) {
                letters += (char) (random.nextInt(26) + 'A');
            }

            // Generate 3 random digits
            String numbers = String.format("%03d", random.nextInt(1000));

            order.setOrderCode(letters + numbers);
            order.setOrderDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
            order.setExpectedDeliveryTime(expectedDeliveryTime);
            order.setAccount(accountService.getByUsername(userDetails.getUsername()));
            order.setTotalAmount(Double.parseDouble(totalAmount));
            order.setShippingFee(new BigDecimal(checkoutDTO.getShippingFee()));
            order.setShippingCode(shippingCode);
            order.setOrderStatus(STATUS.WATING_DELIVERY);
//        order = orderService.createOrder(order);
            final Order createdOrder = orderService.createOrder(order);
            cart.getItems().forEach(orderItem -> {
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
            createdOrder.setOrderStatus(STATUS.WATING_PAYMENT);
            orderService.createOrder(createdOrder);

            cartService.deleteCart(cart);

            redirectAttrs.addFlashAttribute("success", "Order created successfully.");
            model.addAttribute("response", responseData);
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to create order. Please try again.");
        }

        model.addAttribute("invoice", order);
        return "orderResult";
    }

}