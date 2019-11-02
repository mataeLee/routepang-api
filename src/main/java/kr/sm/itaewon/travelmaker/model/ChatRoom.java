package kr.sm.itaewon.travelmaker.model;

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
import java.util.UUID;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
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
    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    private List<Customer> customers;

    @CreationTimestamp
    private Timestamp regDate;
    @UpdateTimestamp
    private Timestamp updateDate;
}