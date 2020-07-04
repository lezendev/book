package com.bookhelper.together.Service;

import com.bookhelper.together.domain.Invitation;
import com.bookhelper.together.domain.Manager;
import com.bookhelper.together.repository.InvitationRepository;
import com.bookhelper.together.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final InvitationRepository invitationRepository;

    @Transactional(readOnly = false)
    public Long join(Manager manger){
        Invitation invitation = validateDuplicateInvitation(manger);
        managerRepository.save(manger);
        invitation.setAvailable(false);
        return manger.getId();
    }

    //유효성 검사 (중복, 존재하는지)
    private Invitation validateDuplicateInvitation(Manager manager) {
        //관리자 -> 초대장 중복 사용불가, 없는 초대장도 사용 불가.

        List<Invitation> findInvitations = invitationRepository.findByCode(manager.getInvitation().getCode());

        //이거 전에 IndexOutOfBoundsException 에러가 미리 뜸...
        if (findInvitations.isEmpty()){
            throw new IllegalStateException("유효하지 않은 초대장입니다.");
        }

        if(!findInvitations.get(0).isAvailable()){
            throw new IllegalStateException("이미 사용된 초대장입니다.");
        }

        return findInvitations.get(0);
    }

    public List<Manager> findManagers(){
        return managerRepository.findAll();
    }

    public Manager findById(Long id){
        return managerRepository.findOne(id);
    }
}
