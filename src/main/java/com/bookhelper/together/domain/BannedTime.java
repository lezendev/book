package com.bookhelper.together.domain;

import com.bookhelper.together.domain.Room;
import com.bookhelper.together.domain.Timetable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class BannedTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banned_id")
    private Long id;

    @Embedded
    private Timetable timetable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

}
