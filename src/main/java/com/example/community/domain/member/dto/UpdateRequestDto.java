package com.example.community.domain.member.dto;

import lombok.Builder;

@Builder
public record UpdateRequestDto(
        String username,
        String password,
        String email
) {
}
