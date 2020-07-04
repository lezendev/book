package com.bookhelper.together.Service;


import com.bookhelper.together.domain.Invitation;
import com.bookhelper.together.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;

    @Transactional(readOnly = false)
    public Long issue(){

        Invitation invitation = new Invitation();
        String code = createCode();
        invitation.setCode(code);

        invitationRepository.save(invitation);

        return invitation.getId();
    }

    public List<Invitation> findInvitations(){
        return invitationRepository.findAll();
    }

    public Invitation findByCode(String code){
        return invitationRepository.findByCode(code).get(0);
    }

    private String createCode(){

        String code = randomWord(4);
        List<Invitation> findInvitations = invitationRepository.findByCode(code);

        if(!findInvitations.isEmpty()){

            String newCode = randomWord(4);
            while (!findInvitations.isEmpty()){
                findInvitations = invitationRepository.findByCode(newCode);
                newCode = randomWord(4);
            }

            return newCode;
        }

        return code;
    }







    private String randomWord(int wordLength){

        Random r = new Random();
        StringBuilder sb = new StringBuilder(wordLength);

        for(int i = 0; i < wordLength; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }

        return sb.toString().toUpperCase();
    }


}
