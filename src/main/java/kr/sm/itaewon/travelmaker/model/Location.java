package kr.sm.itaewon.travelmaker.model;


import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import kr.sm.itaewon.travelmaker.category.LocationCategory;
import kr.sm.itaewon.travelmaker.util.JsonToPointDeserializer;
import kr.sm.itaewon.travelmaker.util.PointToJsonSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

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
//
//    @JsonSerialize(using = GeometrySerializer.class)
//    @JsonDeserialize(using = JsonToPointDeserializer.class)
//    @Column(columnDefinition = "Geometry",name="coordinates")
//    private Geometry coordinates;

    @Column(name="longitude")//경도
    private double longitude;

    @Column(name="latitude")//위도
    private double latitude;

    @Column(name="place_id")
    private String placeId;

    @Column(name="address")
    private String address;

    @Column(name="name")
    private String name;

    @Column(name="used")
    private double used;

    @Enumerated(EnumType.ORDINAL)
    private LocationCategory category;

    private int articleCount;
}