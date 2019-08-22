package kr.sm.itaewon.travelmaker.model;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="basket_id")
    private long basketId;

    @Column(name="customer_id")
    private long customerId;

    @Column(name="location_id")
    private long locationId;

    @Column(name="route_id")
    @ColumnDefault("-1")
    private long routeId;
}
