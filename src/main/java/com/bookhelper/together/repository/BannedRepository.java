package com.bookhelper.together.repository;

import com.bookhelper.together.domain.BannedTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BannedRepository {

    private final EntityManager em;

    public void save (BannedTime bannedTime){
        em.persist(bannedTime);
    }

    public BannedTime findOne(Long id){
        return em.find(BannedTime.class, id);
    }

    public List<BannedTime> findAll(){
        return em.createQuery("select t from BannedTime t", BannedTime.class)
                .getResultList();
    }

    // 무엇을 검색할것인지?
    public List<BannedTime> findByRoom(Long roomId){
        return em.createQuery("select t from BannedTime t where t.room.id = :room order by t.timetable.localDate, t.timetable.startTime desc ", BannedTime.class)
                .setParameter("room", roomId)
                .getResultList();

    }

    public List<BannedTime> findByTitleLike(String title){
        title = "%"+title+"%";
        return em.createQuery("select t from BannedTime t where t.title like :title")
                .setParameter("title", title)
                .getResultList();

    }

    //겹치는거 (6.27 아직확인안함)
    public Object countDuplicateTime(BannedTime bannedTime){

        LocalDate localDate = bannedTime.getTimetable().getLocalDate();
        String startTime = bannedTime.getTimetable().getStartTime();
        String endTime = bannedTime.getTimetable().getEndTime();
        Long roomId = bannedTime.getRoom().getId();

        Object cnt = em.createQuery("select count(t) from BannedTime t where t.timetable.localDate = :localDate " +
                "and t.timetable.startTime < :endTime " +
                "and t.timetable.endTime > : startTime " +
                "and t.room.id = :roomId")
                .setParameter("localDate", localDate)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .setParameter("roomId", roomId)
                .getSingleResult();

        return cnt;

    }

    public void remove(BannedTime bannedTime){
        em.remove(bannedTime);
    }


}
