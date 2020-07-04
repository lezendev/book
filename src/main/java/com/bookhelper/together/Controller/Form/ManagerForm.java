package com.bookhelper.together.Controller.Form;

import com.bookhelper.together.domain.Invitation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ManagerForm {

    @NotEmpty(message = "관리자 이름은 필수입니다")
    private String name;
    private String phoneNumber;

    @NotEmpty(message = "초대장 입력은 필수입니다")
    private String invitation;

}
