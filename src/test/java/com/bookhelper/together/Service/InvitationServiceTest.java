package com.bookhelper.together.Service;

import com.bookhelper.together.domain.Invitation;
import com.bookhelper.together.repository.InvitationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class InvitationServiceTest {

    @Autowired EntityManager em;
    @Autowired InvitationRepository invitationRepository;
    @Autowired InvitationService invitationService;

    @Test
    public void 초대장발급(){
        

        for (int i = 0 ; i <= 5 ; i++ ){
            invitationService.issue();
        }




        List<Invitation> in = invitationService.findInvitations();

        em.flush();

        

        System.out.println("=====================");
        for (int i = 0 ; i <= 5 ; i++ ){
            System.out.println("CODE : "+in.get(i).getCode());
        }
        System.out.println("=====================");

    }


}