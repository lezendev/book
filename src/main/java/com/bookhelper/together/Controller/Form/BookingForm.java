package com.bookhelper.together.Controller.Form;

import com.bookhelper.together.domain.TableStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter @Setter
public class BookingForm {


    private LocalDate localDate;

    private String time;

    private String startTime;
    private String endTime;

    private TableStatus tableStatus;


    protected BookingForm(){

    }

    public BookingForm(LocalDate localDate, String time, String startTime, String endTime, TableStatus tableStatus) {
        this.localDate = localDate;
        this.time = time;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tableStatus = tableStatus;

    }
}



