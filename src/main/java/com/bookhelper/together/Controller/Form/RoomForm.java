package com.bookhelper.together.Controller.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class RoomForm {

    private String id;

    @NotEmpty(message = "방 이름은 필수입니다")
    private String title;

    private String content;

    @NotEmpty(message = "시작 날짜 설정은 필수입니다")
    private String startDate;



    /**
     * 이 부분은 나중에 지울 부분임 (지금은 기능이 없어서 포함해놓음)
     **/
    private String manager;

}
