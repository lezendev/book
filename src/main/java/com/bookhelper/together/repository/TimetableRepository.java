package com.bookhelper.together.repository;

import com.bookhelper.together.domain.Invitation;
import com.bookhelper.together.domain.Timetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimetableRepository {

    private final EntityManager em;

    public void save (Timetable timetable){
        em.persist(timetable);
    }

    public Timetable findOne(Long id){
        return em.find(Timetable.class, id);
    }

    public List<Timetable> findAll(){
        return em.createQuery("select t from Timetable t", Timetable.class)
                .getResultList();
    }

    public List<Timetable> findByCode(String code){
        return em.createQuery("select i from Timetable i where i.code = :code", Timetable.class)
                .setParameter("code", code)
                .getResultList();
    }

    //겹치는거 (6.27 아직확인안함)
    public String validateDuplicateTime(LocalDate localDate, String startTime, String endTime){
         em.createQuery("select count(t) from Timetable t where t.localDate = :localDate " +
                "and t.startTime < :endTime " +
                "and t.endTime > : startTime")
                .setParameter("localDate", localDate)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .getResultList();

        return "";

        //cnt 가 0이면 중복이 없는것
        //cnt 이면 뭔가 걸렸다는 뜻 -->>>>> 중복이 있음. 추가불가능
    }
}
