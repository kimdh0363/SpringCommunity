package com.example.community.domain.member.controller;

import com.example.community.domain.member.dto.SignInRequestDto;
import com.example.community.domain.member.dto.SignUpRequestDto;
import com.example.community.domain.member.dto.UpdateRequestDto;
import com.example.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members") //엔드포인트
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(memberService.signIn(signInRequestDto));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> delete(@PathVariable("memberId") Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.ok("회원탈퇴 성공");
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<?> update(@PathVariable("memberId") Long memberId, @RequestBody UpdateRequestDto updateRequestDto) {
        memberService.update(memberId, updateRequestDto);
        return ResponseEntity.ok("회원 수정 성공");
    }
}
