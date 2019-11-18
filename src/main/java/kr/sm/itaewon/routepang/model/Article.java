package kr.sm.itaewon.routepang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Article{

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long articleId;

    /**
     *  업로드 이미지
     */
    @Column(name="image", length = 1000)
    private String image;

    /**
     *  간단한 후기
     */
    @Column(name="summary", length = 1000)
    private String summary;

    /**
     *  연결된 지역 id
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     *  작성자
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;

    /**
     *  외부 블로그 및 sns 게시글 링크
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(targetEntity = Link.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "link_id")
    private Link link;

    /**
     *  작성일
     */
    @CreationTimestamp
    private Timestamp regDate;

    /**
     *  수정일
     */
    @UpdateTimestamp
    private Timestamp updateDate;

}