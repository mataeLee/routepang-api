package kr.sm.itaewon.travelmaker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    /**
     *  이메일
     */
    @Column(name="email")
    private String email;

    /**
     *  계정 정보 생성 날짜
     */
    @CreationTimestamp
    private Timestamp regDate;

    /**
     *  계정 정보 업데이트 날짜
     */
    @UpdateTimestamp
    private Timestamp updateDate;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Role role;
}
