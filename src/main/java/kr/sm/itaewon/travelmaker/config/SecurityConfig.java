package kr.sm.itaewon.travelmaker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 허용되어야 할 경로들
        web.ignoring().antMatchers("/resources/**");// #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 로그인 설정
        http.authorizeRequests()
                    .antMatchers("/","/login").permitAll()
                    .antMatchers().hasRole("USER")
                    // 장바구니
                    .antMatchers("/basket/**").hasRole("USER")
                    // 채팅
                    .antMatchers("/chat/**").hasRole("USER")
                    // 링크 공유
                    .antMatchers("/link/**").hasRole("USER")
                    //
                    .antMatchers("/wishItem/**").hasRole("USER")
                    .antMatchers("/wishItem/**").hasRole("USER")
                    // 마이페이지 관리
                    .antMatchers("/customer/**").hasRole("USER")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();


//        http.authorizeRequests()
//                // ROLE_USER, ROLE_ADMIN으로 권한 분리 유알엘 정의
//                .antMatchers("/", "/user/login", "/error**").permitAll()
//                // 장바구니
//                .antMatchers("/basket/**").access("ROLE_USER")
//                // 채팅
//                .antMatchers("/chat/**").access("ROLE_USER")
//                // 링크 공유
//                .antMatchers("/link/**").access("ROLE_USER")
//                //
//                .antMatchers("/wishItem/**").access("ROLE_USER")
//                .antMatchers("/wishItem/**").access("ROLE_USER")
//                // 마이페이지 관리
//                .antMatchers("/customer/**").access("ROLE_USER")
//                //
//                .antMatchers("/**").authenticated()
//                .and()
//                // 로그인 페이지 및 성공 url, handler 그리고 로그인 시 사용되는 id, password 파라미터 정의
//                .formLogin()
//                .defaultSuccessUrl("/")
////                .failureHandler(authFailureHandler)
////                .successHandler(authSuccessHandler)
////                .usernameParameter("id")
////                .passwordParameter("password")
//                .and()
//                // 로그아웃 관련 설정
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/customer/logout"))
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .and()
//                // csrf 사용유무 설정
//                // csrf 설정을 사용하면 모든 request에 csrf 값을 함께 전달해야한다.
//                .csrf()
//                .and()
//                // 로그인 프로세스가 진행될 provider
//                .authenticationProvider(authProvider);


    }
}
