package fptu.mobile_shop.MobileShop.security;


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
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/admin/dashboard").hasAnyRole("ADMIN")
                            .requestMatchers("/admin/product").hasAnyRole("STAFF","ADMIN")
                            .requestMatchers("/admin/members").hasAnyRole("STAFF","ADMIN")
                            .requestMatchers("/admin/orders").hasAnyRole("STAFF","ADMIN")
                            .requestMatchers("/admin/blogs").hasAnyRole("STAFF", "ADMIN")
//                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/staff/**").hasRole("STAFF")
                            .requestMatchers("/api/feedback/","/admin/feedback","/admin/feedback/").permitAll()
                            .anyRequest().permitAll();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/home")
                            .failureHandler((request, response, exception) -> {
                                response.sendError(401, "Invalid Username Or Password");
                            });
                }).logout(httpSecurityLogoutConfigurer -> {
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



