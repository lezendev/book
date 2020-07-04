package com.bookhelper.together.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String title;
    private String content;

    //방에 설정되어 있는 시작, 끝 날짜 (예약기간)
    private LocalDate startDate;
    private LocalDate endDate;

    //방 관리자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    //금지테이블 (양방향이기 때문에 일단 사용X)
/*    @OneToMany(mappedBy = "room")
    private List<BannedTime> bannedTimes = new ArrayList<>();*/









}
