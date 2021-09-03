package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Customer;
import kr.sm.itaewon.routepang.model.Role;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    public Customer signup(Customer customer){

        Customer customerModel = customerService.findByAccount(customer.getAccount());
        if(customerModel != null){
            return null;
        }
        customerModel = new Customer();
        Role role = new Role();
        role.setRoleName("USER");

        //customerModel.setRole(role);
        customerModel.setReference(customer.getReference());
        customerModel.setEmail(customer.getEmail());
        customerModel.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt()));
        customerModel.setAccount(customer.getAccount());

        return customerModel;
    }

    public String signin(Customer customer, String account, String password){

        // 로그인 id 검증
        if(!customer.getAccount().equals(account))
            return null;

        // 비밀번호 검증
        if(BCrypt.checkpw(password, customer.getPassword())){

            // 로그인 토큰 발행
            String token = jwtService.create("customer", customer, "user");

            customer.setLoginToken(token);
            customerService.save(customer);
            return token;
        }
        return null;
    }

    //TODO logout 요청 처리(세션 관리)
    public void logout(Customer customer) {
        customer.setLoginToken("");
        customer.setPushToken("");
        customerService.save(customer);
    }
}
