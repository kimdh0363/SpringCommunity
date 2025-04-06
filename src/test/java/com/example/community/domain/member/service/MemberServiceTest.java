package com.example.community.domain.member.service;

import com.example.community.domain.member.dto.SignInRequestDto;
import com.example.community.domain.member.dto.SignUpRequestDto;
import com.example.community.domain.member.dto.UpdateRequestDto;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("사용자로부터 받은 정보를 통해 회원가입을 진행한다.")
    @Test
    void signUp() {
        //given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .username("Test")
                .password("Test")
                .email("Test@Test.com")
                .build();

        //when
        memberService.signUp(signUpRequestDto);

        //then
        Long memberId = 1L;
        Optional<Member> member = memberRepository.findById(memberId);

        assertThat(member).isNotNull();
        assertThat(member.get().getUsername()).isEqualTo(signUpRequestDto.username());
    }

    @DisplayName("이미 존재하는 이메일로 회원가입을 시도하면 예외가 발생한다,")
    @Test
    void signUpThrowExceptionCausedByDuplicateEmail() throws Exception {
        Member member = createMember();

        memberRepository.save(member);

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .username("Test")
                .password("Test")
                .email("Test@Test.com")
                .build();

        assertThatThrownBy(() -> memberService.signUp(signUpRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }

    @DisplayName("사용자로부터 정보를 입력받아 로그인 진행한다.")
    @Test
    void signIn() {
        //given
        Member member = createMember();

        memberRepository.save(member);

        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .email("Test@Test.com")
                .password("Test")
                .build();
        //when
        String result = memberService.signIn(signInRequestDto);
        //then
        assertThat(result).isEqualTo("로그인 성공");
    }

    @DisplayName("존재하지 않는 이메일을 받아오면 예외가 발생한다.")
    @Test
    void signInThrowExceptionCauseByNotExistEmail() {
        Member member = createMember();

        memberRepository.save(member);

        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .email("Test123@Test.com")
                .password("Test")
                .build();

        assertThatThrownBy(() -> memberService.signIn(signInRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 이메일입니다.");

    }

    @DisplayName("비밀번호가 틀리면 예외가 발생한다")
    @Test
    void signInThrowExceptionCauseByNotEqualsPassword() {
        Member member = createMember();

        memberRepository.save(member);
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .email("Test@Test.com")
                .password("Test1234")
                .build();

        assertThatThrownBy(() -> memberService.signIn(signInRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 틀렸습니다.");

    }

    @DisplayName("사용자에게 받은 ID를 통해 회원탈퇴를 진행한다.")
    @Test
    void delete() {
        Member member = createMember();

        memberRepository.save(member);

        Long memberId = member.getId();
        memberService.delete(memberId);

        Optional<Member> deleteMember = memberRepository.findById(memberId);
        assertThat(deleteMember).isNotPresent();
    }

    @DisplayName("사용자에게 받은 정보를 통해 회원수정을 진행한다")
    @Test
    void update() {
        Member member = createMember();
        memberRepository.save(member);

        Long memberId = member.getId();

        UpdateRequestDto updateRequestDto = UpdateRequestDto.builder()
                .username("Test123")
                .password("Test123")
                .email("Test123@Test.com")
                .build();

        memberService.update(memberId, updateRequestDto);

        assertThat(updateRequestDto.username()).isEqualTo("Test123");
        assertThat(updateRequestDto.password()).isEqualTo("Test123");
        assertThat(updateRequestDto.email()).isEqualTo("Test123@Test.com");


    }
    public static Member createMember() {
        return Member.builder()
                .username("Test")
                .password("Test")
                .email("Test@Test.com")
                .build();

    }
}