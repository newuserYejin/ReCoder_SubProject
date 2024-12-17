package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteDTO {

    private int postId;                     // 게시물 ID
    private String voteTitle;               // 투표 제목
    private String voteContent;             // 투표 내용
    private LocalDateTime voteCreationDate; // 투표 시작일
    private LocalDateTime voteAndDate;      // 투표 종료일

}
