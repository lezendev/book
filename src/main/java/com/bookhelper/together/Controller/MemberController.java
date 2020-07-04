package com.bookhelper.together.Controller;

import com.bookhelper.together.Controller.Form.MemberForm;
import com.bookhelper.together.Service.MemberService;
import com.bookhelper.together.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
    * 전화번호 입력 방식을 3칸으로 나누어 할지 -빼고 해달라고 할지 결정해야 함.
    * */

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") //실제로 입력받음
    public String create(@Valid MemberForm form, BindingResult result){

        if (result.hasErrors()){
            return "members/createMemberForm";
            //다시 원래페이지로 돌아옴
        }

        StringBuilder phoneNumber =  new StringBuilder();
        phoneNumber.append(form.getPhoneNumber1().trim());
        phoneNumber.append("-");
        phoneNumber.append(form.getPhoneNumber2().trim());
        phoneNumber.append("-");
        phoneNumber.append(form.getPhoneNumber3().trim());


        Member member = new Member();
        member.setName(form.getName());
        member.setPhoneNumber(phoneNumber.toString());

        memberService.join(member);
        return "redirect:/";

    }


    @GetMapping("/members")
    public String List(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }








}
