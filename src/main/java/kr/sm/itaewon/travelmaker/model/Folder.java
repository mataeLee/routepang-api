package kr.sm.itaewon.travelmaker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

//@Entity
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
public class Folder {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="id")
//    private long folderId;
//
//    @Column(name="title")
//    @ColumnDefault("no title")
//    private String title;
//
//    @Column(name="boundary")
//    @ColumnDefault("unknown")
//    private String boundary;
//
//    @Column(name="reg_date")
//    private Timestamp regDate;
//
//    @Column(name="travel_date")
//    private Timestamp travelDate;
//
//    @Column(name="parent_id")
//    @ColumnDefault("0")
//    private long parentId;
//
//    @Transient
//    private List<Folder> folders;
}
