package kr.sm.itaewon.routepang.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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
    @Column(name="customer_id")
    private long customerId;

    /**
     *  위시리스트 item
     */
    @Column(name="location_id")
    private long locationId;

    /**
     *  어떤 루트에 포함된 item인지
     */
    @Column(name="route_id")
    @ColumnDefault("0")
    private long routeId;
}
