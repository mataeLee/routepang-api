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
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.Point;


import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Location{

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long locationId;

    /**
     *  좌표(4326 좌표계)
     *  org.locationtech.jts.geom.Point
     *
     */
    @JsonSerialize(using = GeojsonSerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @Column(name="coordinates")
    private Point coordinates;

    /**
     *  대표 이미지
     */
    //TODO 찬영이의 크롤링 이용하여 가져오기
    @Transient
    //@Column(name="image")
    private String image;

    /**
     *  장소 place id
     */
    @Column(name="place_id")
    private String placeId;

    /**
     *  주소
     */
    @Column(name="address", length = 1000)
    private String address;

    /**
     *  지명 place name
     */
    @Column(name="name", length = 1000)
    private String name;

    /**
     *  소요 시간
     */
    @Column(name="used_time")
    private double usedTime;

    /**
     *  장소 타입 (ex.음식점, 명소)
     */
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault("1")
    private LocationCategory category;

    /**
     *  평점
     */
    @Column(name="rating")
    @ColumnDefault("0")
    private float rating;

    /**
     *  관련 게시글 갯수
     */
    @Transient
    private int articleCount;
}