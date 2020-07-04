package com.bookhelper.together.Controller;

import com.bookhelper.together.Controller.Form.ManagerForm;
import com.bookhelper.together.Service.InvitationService;
import com.bookhelper.together.Service.ManagerService;
import com.bookhelper.together.domain.Invitation;
import com.bookhelper.together.domain.Manager;
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
public class ManagerController {

    private final ManagerService managerService;
    private final InvitationService invitationService;

    @GetMapping("/managers/new")
    public String createForm(Model model){
        model.addAttribute("managerForm", new ManagerForm());
        return "managers/createManagerForm";
    }

    @PostMapping("/managers/new")
    public String create(@Valid ManagerForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "managers/createManagerForm";
            //다시 원래페이지로 돌아옴
        }

        Manager manager = new Manager();
        manager.setName(form.getName());
        manager.setPhoneNumber(form.getPhoneNumber());

        //1259 이것도 문제아닌거같은데??
        Invitation invitation = invitationService.findByCode(form.getInvitation());
        manager.setInvitation(invitation);

        managerService.join(manager);


        return "redirect:/";
    }



    @GetMapping("/managers")
    public String List(Model model){
        List<Manager> managers = managerService.findManagers();
        model.addAttribute("managers", managers);
        return "managers/managerList";
    }

}
