package kr.sm.itaewon.routepang.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
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
    @Column(name="id")
    private long customerId;

    /**
     *  계정 id
     */
    @Column(name="account")
    private String account;

    /**
     *  비밀번호
     */
    @Column(name="password", length = 1000)
    private String password;

    /**
     *  닉네임
     */
    @Column(name="reference")
    private String reference;

    /**
     *  이메일
     */
    @Column(name="email")
    private String email;

    @OneToOne(mappedBy = "customer")
    private Basket basket;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();


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

    @Transient
    private String token;
    //TODO 권한
//    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
//    private Role role;
//
//    @Transient
//    @JsonIgnore
//    public List<Role> getRoles(){
//        List<Role> roles = new ArrayList<>();
//        roles.add(role);
//        return roles;
//    }


}