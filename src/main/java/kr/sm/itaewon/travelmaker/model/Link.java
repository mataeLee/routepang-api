package kr.sm.itaewon.travelmaker.model;

import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "Id_Generator", sequenceName = "Id", initialValue = 1, allocationSize = 1)
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Id")
    @Column(name="link_id")
    private long linkId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="link_url")
    private String linkUrl;

    @Column(name="favicon_url")
    private String favicon;

    @Column(name="image_url")
    private String image;

    @Column(name="summary")
    private String summary;

}