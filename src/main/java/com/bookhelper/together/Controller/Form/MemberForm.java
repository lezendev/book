package com.bookhelper.together.Controller.Form;



import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이름 입력은 필수입니다")
    private String name;

    @NotEmpty(message = "전화번호 입력은 필수입니다")
    private String phoneNumber1;

    @NotEmpty(message = "전화번호 입력은 필수입니다")
    private String phoneNumber2;

    @NotEmpty(message = "전화번호 입력은 필수입니다")
    private String phoneNumber3;


}
