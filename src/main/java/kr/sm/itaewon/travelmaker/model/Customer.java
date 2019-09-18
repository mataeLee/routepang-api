package kr.sm.itaewon.travelmaker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Customer {

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private long customerId;

    /**
     *  계정 id
     */
    @Column(name="account_id")
    private String account;

    /**
     *  비밀번호
     */
    @Column(name="password", length = 1000)
    private String password;

    /**
     *  닉네임
     */
    @Column(name="name")
    private String name;

}
