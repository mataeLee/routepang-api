package kr.sm.itaewon.routepang.interceptor;

import kr.sm.itaewon.routepang.exception.UnauthorizedException;
import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "Authorization";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("jwt interceptor intercept!");
        final String token = request.getHeader(HEADER_AUTH);

        if (token != null && jwtService.isUsable(token)) {
            return true;
        } else {
            throw new UnauthorizedException();
        }
    }
}
