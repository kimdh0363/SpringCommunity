package com.example.community.domain.member.service;

import com.example.community.domain.member.dto.DeleteRequestDto;
import com.example.community.domain.member.dto.SignInRequestDto;
import com.example.community.domain.member.dto.SignUpRequestDto;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private  final MemberRepository memberRepository;

    //회원가입

    public void signUp(SignUpRequestDto signUpRequestDto) { // 입력된 값 받아오기

        // 2. 이메일, 사용자 닉네임 중복 검사
        boolean isEmailDuplicate = memberRepository.existsByEmail(signUpRequestDto.email());

        if(isEmailDuplicate) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 3. 엔티티를 만들어서 Repository 저장
        Member member = Member.builder()
                .username(signUpRequestDto.username())
                .password(signUpRequestDto.password())
                .email(signUpRequestDto.email())
                .build();

        memberRepository.save(member);
    }

    //로그린

    public String signIn(SignInRequestDto signInRequestDto) {
        // 1.클라이언트 입력
        boolean existMember = memberRepository.existsByEmail(signInRequestDto.email());

        if(!existMember) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }

        boolean checkPassword = memberRepository.existsByPassword(signInRequestDto.password());

        if(!checkPassword) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return "로그인 성공";
    }
    public String delete(DeleteRequestDto deleteRequestDto) {
         boolean existEmail = memberRepository.existsByEmail(deleteRequestDto.email());

         if(!existEmail) {
             throw new IllegalArgumentException("존재하는 회원이 아닙니다.");
         }

         boolean checkPassword = memberRepository.existsByPassword(deleteRequestDto.password());

         if (!checkPassword) {
             throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
         }

         Member member = Member.builder()
                 .username(deleteRequestDto.username())
                 .password(deleteRequestDto.password())
                 .email(deleteRequestDto.email())
                 .build();

         memberRepository.delete(member);

         return "회원탈퇴 성공";
    }

}
