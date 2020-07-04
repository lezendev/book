package com.bookhelper.together.Controller.Form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TimetableForm {

    private String title;
    private LocalDate localDate;

    private String date;

    private String date1;
    private String date2;
    private String date3;

    private String startTime;
    private String endTime;

}
