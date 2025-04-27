package com.example.community.domain.comment.controller;

import com.example.community.domain.comment.dto.CommentSaveRequestDto;
import com.example.community.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/{boardId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable("boardId") Long boardId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        commentService.createComment(boardId,commentSaveRequestDto);
        return ResponseEntity.ok("댓글 저장 완료");
    }

}
