package com.example.community.domain.member.dto;

import lombok.Builder;

@Builder
public record DeleteRequestDto(
        String username,
        String password,
        String email
) {
}
