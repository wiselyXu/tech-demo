package org.wgwj.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.DelegatingLogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:08:00
 * @Description:
 **/

@Configuration
//@EnableMethodSecurity  // 开启基于方法的 自定义配置（在spring boot 项目中也可以省略， 默认也是开启的）
//@EnableWebSecurity // 开启SpringSecurity的自定义配置（在spring boot 项目中可以看到此注解）
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user/list").hasAuthority("USER_LIST")
//                        .requestMatchers("/user/add").hasAuthority("USER_ADD")
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest()  // 对所有的请求开启授权保护
                        .authenticated() // 已认证的进行自动授权
                )
                // .formLogin(withDefaults())   // 默认的登录与登出页面
                .formLogin(form -> form.loginPage("/login").permitAll()  // 无需授权，即可访问此页
                                .failureUrl("/login?error")  // 失败了去哪， 这是默认的
                        //.usernameParameter("mypassword")
                        //.passwordParameter("mypassword")
                        .successHandler(new MyAuthenticationSuccessHandler()) // 认证成功时的处理器
                        .failureHandler(new MyAuthenticationFailureHandler())
                )
                .httpBasic(withDefaults());//使用基本的授权方式
        http.csrf(csrf->csrf.disable());
        http.cors(withDefaults()); // 支持跨域请求声明

        // 登出处理
        http.logout(logout -> {
            logout.logoutSuccessHandler(new MyLogOutSuccessHandler());
        });

        http.exceptionHandling(exception ->{
            exception.authenticationEntryPoint((new MyAuthenticationEntryPoint()));// 请求认证处理
            exception.accessDeniedHandler(new MyAccessDeniedHandler());
        });
        http.sessionManagement(session ->{
            session.maximumSessions(1).expiredSessionStrategy(new MySessionInformationExpiredStrategy());
        });  // 这里的配置是  同一用户只能在 1  个地方登录， 如在其他地方登录， 就会将前面 的剔除， 并按后面的超进处理器处理

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 创建基于内存的用户信息管理器
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        // 使用manager 管理userDetails对象
//        manager.createUser(User.withDefaultPasswordEncoder().username("Wesley").password("abcd1234").roles("USER").build());
//        return manager;
//    }


    /**
     * 由于这里只是 new 下， 返回bean， 所以  还有另一种方式 管理 它， 直接在 DBUserDetailsManager 上面写上 @Component 效果是一样的
     *
     * 实际上， 我直接这么写是会报错的，   “A component required a bean of type 'org.wgwj.controller.config.DBUserDetailsManager' that could not be found.”
     * 原因是  这里面的 返回不要为 UserDetailsService, 要直接 为 DBUserDetailsManager， 虽然两者是父子关系。
     *
     * @return
     */
    @Bean
   // public UserDetailsService userDetailsService() {
    public DBUserDetailsManager userDetailsService() {
        // 创建基于内存的用户信息管理器
        DBUserDetailsManager manager = new DBUserDetailsManager();
        return manager;
    }
}
