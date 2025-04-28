package com.example.community.domain.comment.dto;

public record CommentUpdateRequestDto(
        String content,
        Long memberId
) {
}
