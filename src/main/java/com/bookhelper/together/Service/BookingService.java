package com.bookhelper.together.Service;

import com.bookhelper.together.domain.BannedTime;
import com.bookhelper.together.domain.BookingTime;
import com.bookhelper.together.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {


    private final BookingRepository bookingRepository;

    @Transactional(readOnly = false)
    public Long add(BookingTime bookingTime){

        //validateTime(bookingTime);
        validateDuplicateTime(bookingTime);
        bookingRepository.save(bookingTime);

        return bookingTime.getId();

    }

    public List<BookingTime> findAll (){
        return bookingRepository.findAll();
    }

    public List<BookingTime> findByRoom(Long roomId){
        return bookingRepository.findByRoom(roomId);
    }







    //일단 복사해왔지만 쓸지는 모르겟음.
    private void validateTime(BookingTime bookingTime){
        int start = Integer.parseInt(bookingTime.getTimetable().getStartTime());
        int end = Integer.parseInt(bookingTime.getTimetable().getEndTime());

        if (start >= end){
            throw new IllegalStateException("시작/종료 시간을 다시 설정해주세요.");
        }

    }

    private void validateDuplicateTime(BookingTime bookingTime) {
        Object cnt = bookingRepository.countDuplicateTime(bookingTime);

        if (!cnt.toString().equals("0")) {
            throw new IllegalStateException("중복되지 않은 시간을 선택해주세요.");
        }
    }




}
