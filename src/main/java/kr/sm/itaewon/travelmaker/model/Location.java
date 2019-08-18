package kr.sm.itaewon.travelmaker.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import kr.sm.itaewon.travelmaker.util.JsonToPointDeserializer;
import kr.sm.itaewon.travelmaker.util.PointToJsonSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    private long locationId;

    @JsonSerialize(using = PointToJsonSerializer.class)
    @JsonDeserialize(using = JsonToPointDeserializer.class)
    @Column(columnDefinition="Geometry")
    private Point coordinates;


    @Column(name="place_id")
    private String placeId;

    @Column(name="address")
    private String address;

    @Column(name="name")
    private String name;
}
