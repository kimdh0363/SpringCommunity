package com.example.community.domain.board.repository;

import com.example.community.domain.board.dto.BoardInfoRequestDto;
import com.example.community.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

    Optional<Board> findBoardById(Long boardId);

    Optional<Board> findAllByTitle(String title);
}
