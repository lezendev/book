package com.bookhelper.together.Service;

import com.bookhelper.together.domain.BannedTime;
import com.bookhelper.together.repository.BannedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannedService {

    private final BannedRepository bannedRepository;

    @Transactional(readOnly = false)
    public Long add(BannedTime bannedTime){

        validateTime(bannedTime);
        validateDuplicateTime(bannedTime);
        bannedRepository.save(bannedTime);
        return bannedTime.getId();

    }

    @Transactional(readOnly = false)
    public void delete(Long bannedId){
        BannedTime b = bannedRepository.findOne(bannedId);
        bannedRepository.remove(b);
    }

    public List<BannedTime> findByRoom(Long roomId){
        return bannedRepository.findByRoom(roomId);
    }

    public List<BannedTime> findAll(){ return bannedRepository.findAll(); }

    private void validateTime(BannedTime bannedTime){
        int start = Integer.parseInt(bannedTime.getTimetable().getStartTime());
        int end = Integer.parseInt(bannedTime.getTimetable().getEndTime());

        if (start >= end){
            throw new IllegalStateException("시작/종료 시간을 다시 설정해주세요.");
        }

    }

    private void validateDuplicateTime(BannedTime bannedTime){
        Object cnt = bannedRepository.countDuplicateTime(bannedTime);

        if (!cnt.toString().equals("0")){
            throw new IllegalStateException("중복되지 않은 시간을 선택해주세요.");
        }
    }







}
