package com.bookhelper.together.Service;

import com.bookhelper.together.domain.Invitation;
import com.bookhelper.together.domain.Manager;
import com.bookhelper.together.domain.Room;
import com.bookhelper.together.repository.MemberRepository;
import com.bookhelper.together.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class RoomServiceTest {

    @Autowired
    RoomService roomService;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 방만들기(){

        Invitation invitation = new Invitation();
        invitation.setCode("1234");
        em.persist(invitation);

        Manager manager = new Manager();
        manager.setName("관리자");
        manager.setInvitation(invitation);
        em.persist(manager);

        Room room = new Room();
        room.setTitle("홍길동의 방");
        room.setContent("홍길동의 방입니다.");
        room.setManager(manager);

        Long id = roomService.create(room);

        System.out.println("=================쉽게보기위한절취선=====================");

        em.flush(); //인서트문 볼 수 있음.

        System.out.println("=======================================================");

        assertEquals(room, roomRepository.findOne(id));

    }

    @Test
    public void 방검색(){

        Invitation invitation = new Invitation();
        invitation.setCode("1234");
        em.persist(invitation);

        Manager manager = new Manager();
        manager.setName("관리자");
        manager.setInvitation(invitation);
        em.persist(manager);

        Room room = new Room();
        room.setTitle("홍길동의 방");
        room.setContent("홍길동의 방입니다.");
        room.setManager(manager);
        em.persist(room);


        Room room2 = roomService.findRoomsByTitle("홍").get(0);
        System.out.println("룸2의 이름 : "+room2.getTitle() +","+"룸2의 매니저 : "+room2.getManager().getName());
        assertEquals(room, room2);

        //타이틀Like 검색은 가능. 그러나...
        //매니저 이름으로 검색이 되게 하려면 어떻게 해야 할까???? - 0621 06:46...
        //매니저 리포지토리를 룸 서비스가 받아다가 삐용뚜슝빠슝하면 되지않을까



    }





}