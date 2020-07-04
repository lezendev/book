package com.bookhelper.together.Controller.Form;

import com.bookhelper.together.domain.TableStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ListForm {

    private String time;

    private TableStatus day1;
    private TableStatus day2;
    private TableStatus day3;

    public ListForm(String time, TableStatus day1, TableStatus day2, TableStatus day3) {
        this.time = time;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
    }
}
