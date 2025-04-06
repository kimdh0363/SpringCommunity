package com.example.community.domain.board.controller;

import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.board.service.BoardService;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
