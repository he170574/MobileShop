package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.CartItemDTO;
import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Cart;
import fptu.mobile_shop.MobileShop.security.CustomAccount;
import fptu.mobile_shop.MobileShop.service.AccountService;
import fptu.mobile_shop.MobileShop.service.CartService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {

    @Resource
    private CartService cartService;
    @Resource
    private AccountService accountService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<ResponseDTO> addToCart(Authentication authentication, @RequestBody CartItemDTO cartItemDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (authentication == null) {
                responseDTO.setMessage("No Login");
            } else {

                responseDTO.setMessage("Success");
                CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
                Account account = accountService.getByUsername(customAccount.getUsername());
                int totalCart = cartService.addToCart(account, cartItemDTO.getProductId(), cartItemDTO.getQuantity());

                if (totalCart == 0) {
                    responseDTO.setMessage("Faild");
                }
                responseDTO.setData(totalCart);
            }
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @PostMapping("/remove-cart")
    public ResponseEntity<ResponseDTO> removeFromCart(Authentication authentication, @RequestParam Long cartItemId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (authentication == null) {
                responseDTO.setMessage("No Login");
            } else {

                responseDTO.setMessage("Success");
                CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
                Account account = accountService.getByUsername(customAccount.getUsername());
                Cart cart = cartService.removeFromCart(account, cartItemId);

                responseDTO.setData(cart);
            }
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @GetMapping("/view-cart")
    public ResponseEntity<ResponseDTO> viewCart(Authentication authentication) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (authentication == null) {
                responseDTO.setMessage("No Login");
            } else {

                responseDTO.setMessage("Success");
                CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
                Account account = accountService.getByUsername(customAccount.getUsername());
                Cart cart = cartService.getCart(account);
                List<CartItemDTO> cartItemDTOS = new ArrayList<>();
                cart.getItems().forEach(cartItem -> {
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setQuantity(cartItem.getQuantity());
                    cartItemDTO.setProductId(cartItem.getProduct().getProductID());
                    cartItemDTO.setProductName(cartItem.getProduct().getProductName());
                    cartItemDTO.setProductPrice(cartItem.getProduct().getPrice());
                    cartItemDTO.setImage(cartItem.getProduct().getProductImage());
                    cartItemDTOS.add(cartItemDTO);
                });
                responseDTO.setData(cartItemDTOS);
            }
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<ResponseDTO> updateQuantity(Authentication authentication, @RequestParam Integer productId, @RequestParam int quantity) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (authentication == null) {
                responseDTO.setMessage("No Login");
            } else {

                responseDTO.setMessage("Success");
                CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
                Account account = accountService.getByUsername(customAccount.getUsername());

                responseDTO.setData(cartService.updateQuantity(account, productId, quantity));
            }
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @GetMapping("/total")
    public ResponseEntity<ResponseDTO> getTotalPrice(Authentication authentication) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (authentication == null) {
                responseDTO.setMessage("No Login");
            } else {

                responseDTO.setMessage("Success");
                CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
                Account account = accountService.getByUsername(customAccount.getUsername());
                double total = cartService.getCartTotalPrice(account);

                responseDTO.setData(total);
            }
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

}

