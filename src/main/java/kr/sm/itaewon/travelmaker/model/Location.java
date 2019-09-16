package kr.sm.itaewon.travelmaker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.sm.itaewon.travelmaker.category.LocationCategory;
import kr.sm.itaewon.travelmaker.util.GeojsonSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.geolatte.geom.json.GeometryDeserializer;
import org.geolatte.geom.json.GeometrySerializer;
import org.locationtech.jts.geom.Point;


import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Location{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    private long locationId;

    @JsonSerialize(using = GeojsonSerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @Column(name="coordinates")
    private Point coordinates;

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

    @Column(name="article_count")
    private int articleCount;
}