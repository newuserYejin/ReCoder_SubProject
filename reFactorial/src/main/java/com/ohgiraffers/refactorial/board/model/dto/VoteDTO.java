package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteDTO {

    private String postTitle;                    // 게시물 제목
    private String postContent;                  // 게시물 내용
    private LocalDateTime postCreationDate;             // 게시물 생성 날짜
    private String postId;                       // 게시물 ID
    private String empId;                        // 작성자 사원번호
//    private LocalDateTime postModificationDate;  // 게시물 수정 날짜
    private int categoryCode;                    // 카테고리 코드
    private LocalDateTime voteEndDate;           // 투표 종료날짜
    private String voteItem1;                     // 투표 항목

}
