package kr.sm.itaewon.routepang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.sm.itaewon.routepang.category.RouteCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Route{

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long routeId;

    /**
     *  루트 타입 (폴더, 루트)
     */
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault("1")
    private RouteCategory category;

    /**
     *  루트 제목
     */
    @Column(name="title")
    @ColumnDefault("'no title'")
    private String title;

    /**
     *  지명?
     */
    @Column(name="boundary")
    @ColumnDefault("'unknown'")
    private String boundary;

    /**
     *  여행 시작 날짜
     */
    @Column(name="start_date")
    private Timestamp startDate;

    /**
     *  여행 끝 날짜
     */
    @Column(name="end_date")
    private Timestamp endDate;

    /**
     *  상위 폴더
     */
    @Column(name="parent_id")
    @ColumnDefault("0")
    private long parentId;

    /**
     *  작성자
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "route")
    private List<Product> products = new ArrayList<>();

    /**
     *  생성일
     */
    @CreationTimestamp
    private Timestamp regDate;

    /**
     *  수정일
     */
    @UpdateTimestamp
    private Timestamp updateDate;

    /**
     *  클라이언트에게 전송하기 위한 json 만들기 용도
     */
    @Transient
    private List<Route> routes;
}