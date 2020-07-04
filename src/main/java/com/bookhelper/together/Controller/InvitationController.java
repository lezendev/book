package com.bookhelper.together.Controller;

import com.bookhelper.together.Service.InvitationService;
import com.bookhelper.together.domain.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    //원래는 권한이 있어야 발급 가능
    @GetMapping("/invitations/new")
    public String invitation(){
        invitationService.issue();
        return "redirect:/";
    }

    @GetMapping("/invitations")
    public String List(Model model){
        List<Invitation> invitations = invitationService.findInvitations();
        model.addAttribute("invitations", invitations);
        return "invitations/invitationList";
    }

}
