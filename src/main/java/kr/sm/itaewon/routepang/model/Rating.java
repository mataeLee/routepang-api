package kr.sm.itaewon.routepang.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Rating {

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long ratingId;

    /**
     *  평가지역
     */
    @Column(name="location_id")
    private long locationId;

    /**
     *  평가자
     */
    @Column(name="customer_id")
    private long customerId;

    /**
     *  평점
     */
    @Column(name="rating")
    private float rating;

    /**
     *  소요 시간
     */
    @Column(name="used_time")
    private double usedTime;

    @CreationTimestamp
    private Timestamp regDate;

    @UpdateTimestamp
    private Timestamp updateDate;
}
