package kr.sm.itaewon.travelmaker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    @Column(name="article_id")
    private long articleId;

    /**
     *  연결된 location
     */
    @NotNull
    @Column(name="location_id")
    private long locationId;

    /**
     *  작성자
     */
    @NotNull
    @Column(name="customer_id")
    private long customerId;

    /**
     *  업로드 이미지
     */
    @Column(name="image")
    private String image;

    /**
     *  간단한 후기
     */
    @Column(name="summary")
    private String summary;

    /**
     *  외부 블로그 및 sns 게시글 링크
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(targetEntity = Link.class, fetch = FetchType.LAZY)
    private Link link;

    /**
     *  작성일
     */
    @Column(name="reg_date")
    private Timestamp reg_date;

}