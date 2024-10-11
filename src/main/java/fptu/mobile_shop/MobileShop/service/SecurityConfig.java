package fptu.mobile_shop.MobileShop.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll() // Cho phép truy cập đến các trang đăng ký và đăng nhập
                        .requestMatchers("/").permitAll() // Yêu cầu xác thực cho trang chính
                        .anyRequest().permitAll() // Yêu cầu xác thực cho tất cả các yêu cầu khác
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/**") // Tắt CSRF cho các đường dẫn đăng ký và đăng nhập
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout") // Chuyển hướng đến trang đăng nhập sau khi logout
                        .permitAll()
                )
                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/auth/login")) // Chuyển hướng đến trang đăng nhập nếu không xác thực
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
