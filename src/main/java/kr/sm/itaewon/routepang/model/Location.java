package kr.sm.itaewon.routepang.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.sm.itaewon.routepang.category.LocationCategory;
import kr.sm.itaewon.routepang.util.GeojsonDeserializer;
import kr.sm.itaewon.routepang.util.GeojsonSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;


import javax.persistence.*;
import java.sql.Timestamp;

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
    @JsonDeserialize(using = GeojsonDeserializer.class)
    @Column(name="coordinates")
    private Point coordinates;

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
     *  장소 타입 (ex.음식점, 명소)
     */
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault("0")
    private LocationCategory category;

    /**
     *  평점
     */
    @Transient
    private float rating;
    /**
     *  평가 수
     */
    @Transient
    private int ratingCount;

    /**
     *  소요 시간
     */
    @Transient
    private double usedTime;
    /**
     *  관련 게시글 갯수
     */
    @Transient
    private int articleCount;

    /**
     *  대표 이미지
     */
    //TODO 찬영이의 크롤링 이용하여 가져오기
    @Transient
    //@Column(name="image")
    private String image;

    @CreationTimestamp
    private Timestamp regDate;
    @UpdateTimestamp
    private Timestamp updateDate;
}