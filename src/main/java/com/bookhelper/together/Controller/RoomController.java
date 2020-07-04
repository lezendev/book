package com.bookhelper.together.Controller;

import com.bookhelper.together.Controller.Form.RoomForm;
import com.bookhelper.together.Service.ManagerService;
import com.bookhelper.together.Service.RoomService;
import com.bookhelper.together.domain.Manager;
import com.bookhelper.together.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final ManagerService managerService;

    @GetMapping("/rooms/new")
    public String createForm(Model model){
        model.addAttribute("RoomForm", new RoomForm());
        return "rooms/createRoomForm";
    }

    @PostMapping("/rooms/new") //실제로 입력받음
    public String create(@Valid RoomForm form, BindingResult result){

        if (result.hasErrors()){
            return "members/createRoomForm";
        }

        Room room = new Room();

        room.setTitle(form.getTitle());
        room.setContent(form.getContent());

        LocalDate start = LocalDate.parse(form.getStartDate());
        room.setStartDate(start);

        LocalDate end = start.plusDays(2);
        room.setEndDate(end);

        Manager manager = managerService.findById(Long.parseLong(form.getManager()));
        room.setManager(manager);



        roomService.create(room);
        return "redirect:/";

    }

    @GetMapping("/rooms")
    public String List (Model model){
        List<Room> rooms = roomService.findRooms();
        model.addAttribute("rooms", rooms);
        return "rooms/roomList";
    }
}
