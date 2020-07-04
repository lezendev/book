package com.bookhelper.together.Controller;

import com.bookhelper.together.Controller.Form.BookingForm;
import com.bookhelper.together.Controller.Form.ListForm;
import com.bookhelper.together.Controller.Form.TimetableForm;
import com.bookhelper.together.Service.BannedService;
import com.bookhelper.together.Service.BookingService;
import com.bookhelper.together.Service.RoomService;
import com.bookhelper.together.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimetableController {

    private final BookingService bookingService;
    private final BannedService bannedService;
    private final RoomService roomService;



    /*지금 생각하는 목록을 뽑아야하는 경우
    ==================CASE1==================
    1.관리자가 자기가 관리하는 방 목록을 봄
    2.방 목록을 선택해서 현황 확인
    3.신청한 회원 닉을 확인해서 선택/거절 등

    ==================CASE2==================
    1. 회원이 특정 방에 입장해서
    2. 그 방에 스케줄 현황을 확인
    3. 신청해서 대기 모드에 들어감

    공통점 : 방으로 걸러야 함
    방으로 거른 뒤 3일로 걸러야 함.
    3일 거른 뒤 24시간으로 걸러야 함.



    */





   /**
     * BookingTime Controller
     **/

    @GetMapping("/timetables/booking/roomList")
    public String bookingList (Model model){
        List<Room> rooms = roomService.findRooms();
        model.addAttribute("rooms", rooms);
        return "timetables/booking/roomList";
    }

    @GetMapping("/timetables/booking/{roomId}/tables")
    public String roomTables(@PathVariable("roomId") Long roomId, Model model) {

/*      ==================CASE2==================
        1. 회원이 특정 방에 입장해서
        2. 그 방에 스케줄 현황을 확인
        3. 신청해서 대기 모드에 들어감
        */

        //해당 룸의 스케줄을 다 불러옴
        //먼저 관리자가 설정한 금지시간을 불러오고 그다음에 불가능한 시간을 불러오자.
        List<BannedTime> banned = bannedService.findByRoom(roomId);

        //이제 정렬을 해야함. 전부 돌려서 날짜별/시간별로 분류해야해
        //1회용. 고정된 첫날 날짜.
        LocalDate localDate = banned.get(0).getTimetable().getLocalDate();

        List<BookingForm> first = new ArrayList<>();
        List<BookingForm> second = new ArrayList<>();
        List<BookingForm> third = new ArrayList<>();

        int time = 24;

        //기초값 설정 -> 모든 시간 가능.
        for (int i = 0 ; i < time ; i++){
            first.add(i, new BookingForm(localDate, i+" : 00","", "", TableStatus.AVAILABLE));
            second.add(i, new BookingForm(localDate, i+" : 00","", "", TableStatus.AVAILABLE));
            third.add(i, new BookingForm(localDate, i+" : 00","", "", TableStatus.AVAILABLE));
        }

        for (int i = 0 ; i < banned.size() ; i++){

            //i 설정.
            BannedTime bf = banned.get(i);
            int start = Integer.parseInt(bf.getTimetable().getStartTime());
            int end = Integer.parseInt(bf.getTimetable().getEndTime());

            //첫날인 경우
            //날짜분류 끝 -> 시간 분류 해야함.

            if(localDate.equals(bf.getTimetable().getLocalDate())){

                for (int j = 0; j < end-start ; j++){
                    first.add(start+j, new BookingForm(localDate, start+j+" : 00", bf.getTimetable().getStartTime(), bf.getTimetable().getEndTime(), TableStatus.BANNED));
                }

            }else if(localDate.plusDays(1).equals(bf.getTimetable().getLocalDate())){

                for (int j = 0; j < end-start ; j++){
                    second.add(start+j, new BookingForm(localDate, start+j+" : 00", bf.getTimetable().getStartTime(), bf.getTimetable().getEndTime(), TableStatus.BANNED));
                }


            }else if(localDate.plusDays(2).equals(bf.getTimetable().getLocalDate())){

                for (int j = 0; j < end-start ; j++){
                    third.add(start+j, new BookingForm(localDate, start+j+" : 00", bf.getTimetable().getStartTime(), bf.getTimetable().getEndTime(), TableStatus.BANNED));
                }

            }

        }

        //새 양식 만들기.
        List<ListForm> form = new ArrayList<>();
        for (int z = 6; z < time ; z++){
            form.add(new ListForm(z+" : 00", first.get(z).getTableStatus(), second.get(z).getTableStatus(), third.get(z).getTableStatus()));
        }
        form.add(new ListForm("24 : 00", first.get(0).getTableStatus(), second.get(0).getTableStatus(), third.get(0).getTableStatus()));

        model.addAttribute("title", banned.get(0).getRoom().getTitle());
        model.addAttribute("firstDay", localDate.toString());
        model.addAttribute("secondDay", localDate.plusDays(1));
        model.addAttribute("thirdDay", localDate.plusDays(2));


        model.addAttribute("form", form);


        return "timetables/booking/bookList";
    }





    /**
     * BannedTime Controller
     **/



    //임시
    @GetMapping("/timetables")
    public String BannedList (Model model){
        List<Room> rooms = roomService.findRooms();
        model.addAttribute("rooms", rooms);
        return "timetables/roomList";
    }



    @GetMapping("/timetables/{roomId}/edit")
    public String setBannedTime(@PathVariable("roomId") Long roomId, Model model){

        //이미 추가되어있는 목록 보여주기
        List<BannedTime> tables = bannedService.findByRoom(roomId);
        model.addAttribute("tables", tables);

        //추가할때 날짜 세팅해주기
        Room room = roomService.findRoom(roomId);
        LocalDate date1 = room.getStartDate();
        LocalDate date2 = date1.plusDays(1);
        LocalDate date3 = date1.plusDays(2);

        TimetableForm timetableForm = new TimetableForm();
        timetableForm.setDate1(date1.toString().substring(8,10));
        timetableForm.setDate2(date2.toString().substring(8,10));
        timetableForm.setDate3(date3.toString().substring(8,10));

        timetableForm.setLocalDate(date1);

        timetableForm.setTitle(room.getTitle());

        model.addAttribute("timetableForm", timetableForm);


        return "timetables/tableList";
    }

    @PostMapping("timetables/{roomId}/edit")
    public String updateBannedTime(@PathVariable Long roomId, @ModelAttribute("timetableForm") TimetableForm form){

        LocalDate ld = form.getLocalDate();
        LocalDate localDate = form.getLocalDate();

        for (int i = 0; i < 3 ; i++){
            if(ld.plusDays(i).toString().substring(8,10).equals(form.getDate())){
                localDate = ld.plusDays(i);
            }
        }

        BannedTime bannedTime = new BannedTime();
        bannedTime.setTimetable(new Timetable(localDate, form.getStartTime(), form.getEndTime(), TableStatus.BANNED));
        bannedTime.setRoom(roomService.findRoom(roomId));

        bannedService.add(bannedTime);

        return "redirect:/timetables/{roomId}/edit";
    }

    @GetMapping("/timetables/{tableId}/delete")
    public String deleteBannedTable(@PathVariable("tableId") Long tableId, Model model) {
        System.out.println("====================="+tableId+"=========================");
            bannedService.delete(tableId);
        return "redirect:/";
    }






}
