package kr.sm.itaewon.routepang.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@RedisHash("sesisons")
public class Session {

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long sessionId;

    @NotNull
    @Column(name = "customer_id")
    private long customerId;

    @NotNull
    @Column(name = "login_token")
    private String loginToken;

    @Column(name = "push_token")
    private String pushToken;
    /**
     *  생성일
     */
    @CreationTimestamp
    private Timestamp regDate;

    /**
     *  수정일
     */
    @UpdateTimestamp
    private Timestamp updateDate;
}
