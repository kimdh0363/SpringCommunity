package com.example.community.domain.board.dto;

import lombok.Builder;

@Builder
public record BoardDeleteRequestDto(
        Long memberId
) {
}
