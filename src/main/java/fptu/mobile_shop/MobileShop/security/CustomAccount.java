package fptu.mobile_shop.MobileShop.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class CustomAccount extends User implements Serializable {
    private String role;

    public CustomAccount(String username,
                         String password,
                         Collection<? extends GrantedAuthority> authorities,
                         String role) {
        super(username, password, authorities);
        this.role = role;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else {
            return authentication.getPrincipal().toString();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}