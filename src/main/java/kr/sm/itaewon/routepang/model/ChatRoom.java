package kr.sm.itaewon.routepang.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;


//@Entity
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
public class ChatRoom {

    /**
     *  방 고유 Id
     */
    @Id
    @NotNull
    @Column(name="room_id")
    private String roomId;

    /**
     *  방 이름
     */
    @Column(name="title")
    private String title;

    /**
     *
     */
    @Column(name="customer")
    private List<Customer> customers;

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