package com.example.community.domain.board.dto;

import com.example.community.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record BoardRequestDto(
        Long memberId,
        String title,
        String content
) {
}
