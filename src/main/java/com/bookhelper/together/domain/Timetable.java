package com.bookhelper.together.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
@Getter
public class Timetable {

    private LocalDate localDate;

    // String 이냐 int 냐 고민좀 해봐야함.
    private String startTime;
    private String endTime;

    @Enumerated(EnumType.STRING)
    private TableStatus tableStatus;

    protected Timetable(){

    }

    public Timetable(LocalDate localDate, String startTime, String endTime, TableStatus tableStatus) {
        this.localDate = localDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tableStatus = tableStatus;
    }
}
