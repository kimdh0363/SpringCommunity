package com.example.community.domain.board.dto;

import lombok.Builder;

@Builder
public record BoardInfoResponseDto(
        String title,
        String content,
        Long memberId
) {
}
