package kr.sm.itaewon.routepang.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(targetEntity = Basket.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    @JsonIgnore
    private Basket basket;

    /**
     *  location
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     *  어떤 루트에 포함된 item인지
     */

    //@ManyToMany( cascade = CascadeType.ALL)
    //@JoinColumn(name="route_id")
    @Column(name = "route_id")
    @ColumnDefault("0")
    private long routeId;
}
