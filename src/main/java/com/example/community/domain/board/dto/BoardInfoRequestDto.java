package com.example.community.domain.board.dto;

import com.example.community.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record BoardInfoRequestDto(
        String title,
        String content,
        Long memberId
) {
}
