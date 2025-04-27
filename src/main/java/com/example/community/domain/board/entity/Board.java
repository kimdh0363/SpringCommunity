package com.example.community.domain.board.entity;

import com.example.community.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Board(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void updateBoard(String title, String content) {
        if(title!=null) {
            this.title = title;
        }
        if(content!=null) {
            this.content = content;
        }
    }
}

