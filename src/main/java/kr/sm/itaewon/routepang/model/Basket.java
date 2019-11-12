package kr.sm.itaewon.routepang.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Basket {

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long basketId;

    /**
     *  사용자
     */
    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    /**
     *  장 바구니 아이템들
     */
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    /**
     *  작성일
     */
    @CreationTimestamp
    private Timestamp regDate;

    /**
     *  수정일
     */
    @UpdateTimestamp
    private Timestamp updateDate;
//    @OneToMany
//    private List<Product> products;
}