package com.example.community.domain.board.controller;

import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.dto.BoardUpdateRequestDto;
import com.example.community.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BoardRequestDto boardRequestDto) {
        boardService.createBoard(boardRequestDto.memberId(), boardRequestDto);
        return ResponseEntity.ok("게시글 저장 성공");
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable("boardId") Long boardId) {
        boardService.getBoard(boardId);
        return ResponseEntity.ok("단일 게시글 조회 성공");
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateRequestDto updateRequestDto) {
        boardService.updateBoard(boardId, updateRequestDto);
        return ResponseEntity.ok("게시글 수정 성공");
    }
}
