package kr.sm.itaewon.routepang.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long productId;

    /**
     *  연결된 사용자 장바구니
     */
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    /**
     *  location
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     *  어떤 루트에 포함된 item인지
     */

    @ManyToMany
    @JoinColumn(name="route_id")
    @ColumnDefault("0")
    private List<Route> route = new ArrayList<>();
}