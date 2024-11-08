package fptu.mobile_shop.MobileShop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        response.setContentType("application/json;charset=UTF-8");
        Map<String, String> responseData = new HashMap<>();
        if (roles.contains("ROLE_ADMIN")) {
            responseData.put("redirectUrl", "/admin/dashboard");
            responseData.put("role", "ADMIN");
        } else if (roles.contains("ROLE_STAFF")) {
            responseData.put("redirectUrl", "/admin/product");
            responseData.put("role", "STAFF");
        } else {
            responseData.put("redirectUrl", "/home");
            responseData.put("role", "USER");
        }

        // Write JSON response with redirect URL and role
        new ObjectMapper().writeValue(response.getWriter(), responseData);
    }
}


