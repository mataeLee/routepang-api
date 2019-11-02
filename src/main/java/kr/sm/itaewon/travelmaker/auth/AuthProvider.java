//package kr.sm.itaewon.travelmaker.auth;
//
//import kr.sm.itaewon.travelmaker.repo.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component("authProvider")
//public class AuthProvider implements AuthenticationProvider {
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String id = authentication.getName();
//        String password = HashUtil.sha256(authentication.getCredentials().toString());
//
//        UserDto user = userService.selectUser(new UserDto(id));
//
//        // email에 맞는 user가 없거나 비밀번호가 맞지 않는 경우.
//        if (null == user || !user.getPassword().equals(password)) {
//            return null;
//        }
//
//        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//
//        // 로그인한 계정에게 권한 부여
//        if (user.isIsadmin()) {
//            grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_ADMIN.toString()));
//        } else {
//            grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_USER.toString()));
//        }
//
//        // 로그인 성공시 로그인 사용자 정보 반환
//        return new MyAuthenticaion(id, password, grantedAuthorityList, user);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
