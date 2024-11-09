package fptu.mobile_shop.MobileShop.security;

import fptu.mobile_shop.MobileShop.security.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private CustomLoginSuccessHandler customLoginSuccessHandler;

    public WebSecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/admin/dashboard").hasRole("ADMIN")
                            .requestMatchers("/admin/product", "/admin/members", "/admin/orders", "/admin/blogs","/admin/feedback").hasAnyRole("STAFF", "ADMIN")
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/staff/**").hasRole("STAFF")
//                            .requestMatchers("/api/feedback/","/admin/feedback","/admin/feedback/").permitAll()
                            .anyRequest().permitAll();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .successHandler(customLoginSuccessHandler)
                            .failureHandler((request, response, exception) -> {
                                response.sendError(401, "Invalid Username Or Password");
                            });
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/home")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID");
                });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}