package com.bookhelper.together.Service;


import com.bookhelper.together.domain.Member;
import com.bookhelper.together.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    //논리 연산자... && <-- and , || <-- or
    private void validateDuplicateMember(Member member) {
        //회원 -> 이름이 아니라 같은 전화번호면 동일인으로 간주함.
        List<Member> findMembers = memberRepository.findByPhoneNumber(member.getPhoneNumber());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


}
