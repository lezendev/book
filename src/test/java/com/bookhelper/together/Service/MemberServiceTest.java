package com.bookhelper.together.Service;

import com.bookhelper.together.domain.Member;
import com.bookhelper.together.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입(){

        Member member = new Member();
        member.setName("홍길동");

        Long saveId = memberService.join(member);

        //then
        System.out.println("=================쉽게보기위한절취선=====================");

        em.flush(); //인서트문 볼 수 있음.

        System.out.println("=======================================================");
        assertEquals(member, memberRepository.findByName("홍길동").get(0));

    }

    @Test
    public void 중복가입(){

        Member member1 = new Member();
        member1.setName("홍길동");
        member1.setPhoneNumber("01012345678");

        Member member2 = new Member();
        member2.setName("이기동");
        member2.setPhoneNumber("01012345678");

        //when
        memberService.join(member1);
        memberService.join(member2); //IllegalStateException 발생


        //예외가 발생해야 함

        fail("중복으로 예외가 발생해야 한다.");


    }


}